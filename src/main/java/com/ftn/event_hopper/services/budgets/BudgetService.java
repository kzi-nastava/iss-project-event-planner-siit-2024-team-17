package com.ftn.event_hopper.services.budgets;

import com.ftn.event_hopper.dtos.budgets.BudgetItemManagementDTO;
import com.ftn.event_hopper.dtos.budgets.BudgetManagementDTO;
import com.ftn.event_hopper.dtos.budgets.UpdateBudgetItemDTO;
import com.ftn.event_hopper.mapper.categories.CategoryDTOMapper;
import com.ftn.event_hopper.mapper.events.EventDTOMapper;
import com.ftn.event_hopper.mapper.solutions.ProductDTOMapper;
import com.ftn.event_hopper.models.budgets.BudgetItem;
import com.ftn.event_hopper.models.events.Event;
import com.ftn.event_hopper.models.reservations.Reservation;
import com.ftn.event_hopper.models.solutions.Product;
import com.ftn.event_hopper.models.users.Account;
import com.ftn.event_hopper.models.users.EventOrganizer;
import com.ftn.event_hopper.models.users.PersonType;
import com.ftn.event_hopper.repositories.categoies.CategoryRepository;
import com.ftn.event_hopper.repositories.events.EventRepository;
import com.ftn.event_hopper.repositories.reservations.ReservationRepository;
import com.ftn.event_hopper.repositories.users.EventOrganizerRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class BudgetService {
    @Autowired
    private EventRepository eventRepository;
    @Autowired
    private EventOrganizerRepository eventOrganizerRepository;
    @Autowired
    private CategoryDTOMapper categoryDTOMapper;
    @Autowired
    private ReservationRepository reservationRepository;
    @Autowired
    private ProductDTOMapper productDTOMapper;
    @Autowired
    private EventDTOMapper eventDTOMapper;
    @Autowired
    private CategoryRepository categoryRepository;

    public BudgetManagementDTO findById(UUID eventId) {
        Account account = (Account) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(account == null) {
            throw new EntityNotFoundException("Account not found");
        }
        Event event = eventRepository.findById(eventId).orElseThrow(() -> new EntityNotFoundException("Event not found"));

        if (account.getType() != PersonType.EVENT_ORGANIZER) {
            throw new RuntimeException("You are not authorized to view this budget");
        }

        EventOrganizer organizer = eventOrganizerRepository.findById(account.getPerson().getId()).orElseThrow(() -> new EntityNotFoundException("Event organizer not found"));

        if (!organizer.getEvents().contains(event)) {
            throw new RuntimeException("You are not authorized to view this budget");
        }

        Set<BudgetItem> budgetItems = event.getBudgetItems();

        BudgetManagementDTO budgetManagementDTO = new BudgetManagementDTO();

        List<BudgetItemManagementDTO> budgetItemManagementDTOS = new ArrayList<>();
        for (BudgetItem budgetItem : budgetItems) {
            BudgetItemManagementDTO budgetItemManagementDTO = new BudgetItemManagementDTO();
            budgetItemManagementDTO.setId(budgetItem.getId());
            budgetItemManagementDTO.setAmount(budgetItem.getAmount());
            budgetItemManagementDTO.setCategory(categoryDTOMapper.fromCategoryToSimpleCategoryDTO(budgetItem.getCategory()));
            List<Product> purchasedProducts = new ArrayList<>();
            for (Reservation reservation : reservationRepository.findByEvent(event)) {
                purchasedProducts.add(reservation.getProduct());
            }
            budgetItemManagementDTO.setPurchasedProducts(productDTOMapper.fromProductListToSimpleDTOList(purchasedProducts));
            budgetItemManagementDTO.setDeletable(purchasedProducts.isEmpty());
            budgetItemManagementDTOS.add(budgetItemManagementDTO);
        }
        budgetManagementDTO.setBudgetItems(budgetItemManagementDTOS);

        budgetManagementDTO.setTotalAmount(budgetItems.stream().mapToDouble(BudgetItem::getAmount).sum());
        budgetManagementDTO.setEvent(eventDTOMapper.fromEventToSimpleDTO(event));

        return budgetManagementDTO;
    }

    public BudgetManagementDTO update(UUID eventId, Collection<UpdateBudgetItemDTO> updateBudgetItemDTO) {
        Account account = (Account) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(account == null) {
            throw new EntityNotFoundException("Account not found");
        }
        Event event = eventRepository.findById(eventId).orElseThrow(() -> new EntityNotFoundException("Event not found"));

        if (account.getType() != PersonType.EVENT_ORGANIZER) {
            throw new RuntimeException("You are not authorized to view this budget");
        }

        EventOrganizer organizer = eventOrganizerRepository.findById(account.getPerson().getId()).orElseThrow(() -> new EntityNotFoundException("Event organizer not found"));

        if (!organizer.getEvents().contains(event)) {
            throw new RuntimeException("You are not authorized to view this budget");
        }

        for (UpdateBudgetItemDTO updateBudgetItem : updateBudgetItemDTO) {
            if (updateBudgetItem.getAmount() < 0) {
                throw new RuntimeException("Amount cannot be negative");
            }

            if (updateBudgetItem.getId() == null) {
                categoryRepository.findById(updateBudgetItem.getCategoryId()).orElseThrow(() -> new EntityNotFoundException("Category not found"));
            }

            List<Product> purchasedProducts = new ArrayList<>();
            for (Reservation reservation : reservationRepository.findByEvent(event)) {
                purchasedProducts.add(reservation.getProduct());
            }

            if (updateBudgetItem.getAmount() < purchasedProducts.stream().mapToDouble(product -> product.getCurrentPrice().getFinalPrice()).sum()) {
                throw new RuntimeException("Amount cannot be less than the sum of the prices of the purchased products");
            }

            BudgetItem budgetItem = event.getBudgetItems().stream().filter(budgetItem1 -> budgetItem1.getId().equals(updateBudgetItem.getId())).findFirst().orElseThrow(() -> new EntityNotFoundException("Budget item not found"));

        }

        for (UpdateBudgetItemDTO updateBudgetItem : updateBudgetItemDTO) {
            if (updateBudgetItem.getId() == null) {
                BudgetItem budgetItem = new BudgetItem();
                budgetItem.setAmount(updateBudgetItem.getAmount());
                budgetItem.setCategory(categoryRepository.findById(updateBudgetItem.getCategoryId()).orElseThrow(() -> new EntityNotFoundException("Category not found")));
                event.getBudgetItems().add(new BudgetItem());
            } else {
                event.getBudgetItems().stream()
                        .filter(budgetItem1 -> budgetItem1.getId().equals(updateBudgetItem.getId()))
                        .findFirst()
                        .orElseThrow(() -> new EntityNotFoundException("Budget item not found"))
                        .setAmount(updateBudgetItem.getAmount());
            }
        }

        eventRepository.save(event);
        eventRepository.flush();

        return findById(eventId);
    }
}
