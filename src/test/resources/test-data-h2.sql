INSERT INTO registration_requests (status, "timestamp", id) VALUES (1, '2024-12-07 09:30:00', '5e6d739a-b529-44fb-98db-9cf95b7fe750');
INSERT INTO registration_requests (status, "timestamp", id) VALUES (1, '2024-12-07 09:45:00', '2b221736-8f5b-4c2a-ab96-2a77d7d6274e');
INSERT INTO registration_requests (status, "timestamp", id) VALUES (1, '2024-12-07 10:00:00', '537a4529-8241-4ac4-8d1b-94bb5b50355b');
INSERT INTO registration_requests (status, "timestamp", id) VALUES (1, '2024-12-07 09:30:00', 'dbb94ace-5710-489c-85b3-4fcef244d9b9');
INSERT INTO registration_requests (status, "timestamp", id) VALUES (1, '2024-12-07 09:45:00', 'b7627b61-ffd5-4664-9137-1f7421cbb12f');
INSERT INTO registration_requests (status, "timestamp", id) VALUES (1, '2024-12-07 10:00:00', '53a359d9-f904-4cce-b4f4-cdf32c20552c');
INSERT INTO registration_requests (status, "timestamp", id) VALUES (1, '2024-12-07 09:30:00', 'f18fe9b0-a620-45f9-b3b4-59c9d6929882');
INSERT INTO registration_requests (status, "timestamp", id) VALUES (1, '2024-12-07 09:45:00', '4b7bcaa9-4c40-46d5-a1ba-a22d4e3f9778');
INSERT INTO registration_requests (status, "timestamp", id) VALUES (1, '2024-12-07 10:00:00', 'd123fc95-e98a-473d-bd83-ac3a63a043b4');
INSERT INTO registration_requests (status, "timestamp", id) VALUES (1, '2024-12-07 10:00:00', '48428b06-0175-4830-80fd-595d96f66906');


--
-- Data for Name: accounts; Type: TABLE DATA; Schema: public; Owner: -
--

INSERT INTO accounts (is_active, is_verified, type, suspension_timestamp, id, person_id, registration_request_id, email, password) VALUES (true, true, 3, '2024-12-01 10:00:00', '97d6429d-d755-4337-a2b0-31f54633c538', 'd7b9e5c3-a6f4-49a2-b8c1-7e3f9a2d6b4f', '5e6d739a-b529-44fb-98db-9cf95b7fe750', 'user1@example.com', '$2a$10$Ly.0pBtncTCtqhCnklMFBerUhDO6La0f0ACuwJ/O6i8rowhDOAF36');
INSERT INTO accounts (is_active, is_verified, type, suspension_timestamp, id, person_id, registration_request_id, email, password) VALUES (false, true, 3, NULL, 'd7e7937b-02f3-47d7-bf64-f6e1555ff828', 'b7c9e2d4-a5b3-49c8-b2f7-d4a1c7e6f2d3', '2b221736-8f5b-4c2a-ab96-2a77d7d6274e', 'user2@example.com', '$2a$10$Ly.0pBtncTCtqhCnklMFBerUhDO6La0f0ACuwJ/O6i8rowhDOAF36');
INSERT INTO accounts (is_active, is_verified, type, suspension_timestamp, id, person_id, registration_request_id, email, password) VALUES (true, true, 3, '2024-12-07 14:45:00', '6a1c3423-e400-443f-93c1-9491b9dafb03', '4b9c7f5a-d3e2-42a1-b6c8-3f7e9d5a2c6f', '537a4529-8241-4ac4-8d1b-94bb5b50355b', 'user3@example.com', '$2a$10$Ly.0pBtncTCtqhCnklMFBerUhDO6La0f0ACuwJ/O6i8rowhDOAF36');
INSERT INTO accounts (is_active, is_verified, type, suspension_timestamp, id, person_id, registration_request_id, email, password) VALUES (true, true, 1, '2024-12-01 10:00:00', 'abd3ba1c-76a1-44c5-8204-6cbbcfbfda5d', 'a7c9e5b3-d4f2-49a1-b8c7-3e7f9a5b2c6d', 'dbb94ace-5710-489c-85b3-4fcef244d9b9', 'pup1@example.com', '$2a$10$Ly.0pBtncTCtqhCnklMFBerUhDO6La0f0ACuwJ/O6i8rowhDOAF36');
INSERT INTO accounts (is_active, is_verified, type, suspension_timestamp, id, person_id, registration_request_id, email, password) VALUES (false, true, 1, NULL, '1b02e7df-ea2a-4cb2-b93b-a067a7f12fc6', 'c7a9e5d3-f2b4-4a1b-b8c6-3f9e7a5b2d4f', 'b7627b61-ffd5-4664-9137-1f7421cbb12f', 'pup2@example.com', '$2a$10$Ly.0pBtncTCtqhCnklMFBerUhDO6La0f0ACuwJ/O6i8rowhDOAF36');
INSERT INTO accounts (is_active, is_verified, type, suspension_timestamp, id, person_id, registration_request_id, email, password) VALUES (true, true, 1, '2024-12-07 14:45:00', '31548d6b-019f-492e-ba39-07be7a1433e5', 'd7b5e9c3-a2f4-49b8-b6c1-3f9a7e5b2c8d', '53a359d9-f904-4cce-b4f4-cdf32c20552c', 'pup3@example.com', '$2a$10$Ly.0pBtncTCtqhCnklMFBerUhDO6La0f0ACuwJ/O6i8rowhDOAF36');
INSERT INTO accounts (is_active, is_verified, type, suspension_timestamp, id, person_id, registration_request_id, email, password) VALUES (true, false, 2, '2024-12-01 10:00:00', 'ae7adea0-516c-47b5-ae7b-10abcb32752d', '3f2b7e9a-6d4c-4b8f-b2a1-5c7e3d9f6b2a', 'f18fe9b0-a620-45f9-b3b4-59c9d6929882', 'organizer1@example.com', '$2a$10$Ly.0pBtncTCtqhCnklMFBerUhDO6La0f0ACuwJ/O6i8rowhDOAF36');
INSERT INTO accounts (is_active, is_verified, type, suspension_timestamp, id, person_id, registration_request_id, email, password) VALUES (false, true, 2, NULL, '49a1dae3-323c-460b-bbcd-0fc1132e6bb1', '243d38ad-8ba3-48e0-99bd-0f2e31a710be', '4b7bcaa9-4c40-46d5-a1ba-a22d4e3f9778', 'organzier2@example.com', '$2a$10$Ly.0pBtncTCtqhCnklMFBerUhDO6La0f0ACuwJ/O6i8rowhDOAF36');
INSERT INTO accounts (is_active, is_verified, type, suspension_timestamp, id, person_id, registration_request_id, email, password) VALUES (true, true, 2, '2024-12-07 14:45:00', '035ad44b-2ca3-4775-be2d-c65e6c1eb084', '57c05a8a-dd8b-4da8-af73-90961e423f42', 'd123fc95-e98a-473d-bd83-ac3a63a043b4', 'organizer3@example.com', '$2a$10$Ly.0pBtncTCtqhCnklMFBerUhDO6La0f0ACuwJ/O6i8rowhDOAF36');
INSERT INTO accounts (is_active, is_verified, type, suspension_timestamp, id, person_id, registration_request_id, email, password) VALUES (true, true, 0, '2024-12-01 10:00:00', 'e527b2da-66ae-4076-8301-d3a9dc3c5860', '20343059-c4dd-4608-81aa-104f415e8085', '48428b06-0175-4830-80fd-595d96f66906', 'admin@example.com', '$2a$10$Ly.0pBtncTCtqhCnklMFBerUhDO6La0f0ACuwJ/O6i8rowhDOAF36');


--
-- Data for Name: locations; Type: TABLE DATA; Schema: public; Owner: -
--

INSERT INTO locations (latitude, longitude, id, address, city) VALUES (40.7128, -74.006, 'a7c5e2b9-d3f4-49b8-b6c1-3f9e7a4d5b2c', '123 Main St', 'New York');
INSERT INTO locations (latitude, longitude, id, address, city) VALUES (34.0522, -118.2437, 'b6f3e9d2-c7a5-4b1a-b2c9-8d5e7c6f3a2b', '456 Elm St', 'Los Angeles');
INSERT INTO locations (latitude, longitude, id, address, city) VALUES (51.5074, -0.1278, '4e2d5b9f-a6c3-49a1-b8f5-7d9c7b6e3a2c', '789 Oak St', 'London');
INSERT INTO locations (latitude, longitude, id, address, city) VALUES (51.5064, -0.126, '4e2d5b9f-a6c3-49a1-b8f5-7d9a7b6e3a2c', '790 Oak St', 'London');


--
-- Data for Name: event_organizers; Type: TABLE DATA; Schema: public; Owner: -
--

INSERT INTO event_organizers (type, id, location_id, name, phone_number, profile_picture, surname) VALUES (2, '3f2b7e9a-6d4c-4b8f-b2a1-5c7e3d9f6b2a', '4e2d5b9f-a6c3-49a1-b8f5-7d9c7b6e3a2c', 'John', '1234567890', '', 'Doe');
INSERT INTO event_organizers (type, id, location_id, name, phone_number, profile_picture, surname) VALUES (2, '243d38ad-8ba3-48e0-99bd-0f2e31a710be', 'b6f3e9d2-c7a5-4b1a-b2c9-8d5e7c6f3a2b', 'Alice', '9876543210', '', 'Smith');
INSERT INTO event_organizers (type, id, location_id, name, phone_number, profile_picture, surname) VALUES (2, '57c05a8a-dd8b-4da8-af73-90961e423f42', 'a7c5e2b9-d3f4-49b8-b6c1-3f9e7a4d5b2c', 'Dragan', '1122334455', 'dragan.jpg', 'Nikolic');


--
-- Data for Name: event_types; Type: TABLE DATA; Schema: public; Owner: -
--

INSERT INTO event_types (is_deactivated, id, description, name) VALUES (true, 'f0dcab26-9656-491e-bcce-bf23f723e998', 'Outdoor events', 'Outdoor');
INSERT INTO event_types (is_deactivated, id, description, name) VALUES (false, '5de088d0-7bff-44f5-b144-51d6a24e740b', 'Virtual events', 'Virtual');
INSERT INTO event_types (is_deactivated, id, description, name) VALUES (false, '4e7ec0af-af4e-463b-abe9-d8c2ba317d0a', 'Corporate events', 'Corporate');


--
-- Data for Name: events; Type: TABLE DATA; Schema: public; Owner: -
--

INSERT INTO events (max_attendance, privacy, "time", event_organizer_id, event_type_id, id, location_id, description, name, picture) VALUES (100, 0, '2025-10-20 10:00:00', '57c05a8a-dd8b-4da8-af73-90961e423f42', 'f0dcab26-9656-491e-bcce-bf23f723e998', '3f7b2c9e-4a6f-4d5b-b8c1-7a2f9e3b6d4a', '4e2d5b9f-a6c3-49a1-b8f5-7d9c7b6e3a2c', 'Kontig Tech Meetup inspires and connects tech enthusiasts and innovators. Join engaging talks', 'Tech Meetup', '15.jpeg');
INSERT INTO events (max_attendance, privacy, "time", event_organizer_id, event_type_id, id, location_id, description, name, picture) VALUES (200, 1, '2025-10-18 18:00:00', '57c05a8a-dd8b-4da8-af73-90961e423f42', '5de088d0-7bff-44f5-b144-51d6a24e740b', '2d4a7c9e-6f3b-42a1-b8f5-3c7e9b6a4d5f', 'a7c5e2b9-d3f4-49b8-b6c1-3f9e7a4d5b2c', 'Winter Fest celebrates the magic of the season with enchanting attractions, live performances, ', 'Winter Fest', '16.jpg');
INSERT INTO events (max_attendance, privacy, "time", event_organizer_id, event_type_id, id, location_id, description, name, picture) VALUES (300, 1, '2025-10-18 18:00:00', '243d38ad-8ba3-48e0-99bd-0f2e31a710be', '5de088d0-7bff-44f5-b144-51d6a24e740b', '8c216a1f-6d65-4256-95e0-6a820d5fb902', 'a7c5e2b9-d3f4-49b8-b6c1-3f9e7a4d5b2c', 'Celebrate in style at the Big Birthday Party! Enjoy lively music, delicious food, fun games,.', 'Big Birthday party', '17.jpg');
INSERT INTO events (max_attendance, privacy, "time", event_organizer_id, event_type_id, id, location_id, description, name, picture) VALUES (100, 1, '2025-10-20 18:00:00', '243d38ad-8ba3-48e0-99bd-0f2e31a710be', '5de088d0-7bff-44f5-b144-51d6a24e740b', '06aee816-a4ec-4d3c-9d9c-2c0f6bb96285', 'a7c5e2b9-d3f4-49b8-b6c1-3f9e7a4d5b2c', 'Join the celebration of Queen''s 50th Anniversary, honoring five decades of remarkable achievements.', 'Queens 50th Anniversary', '18.jpg');
INSERT INTO events (max_attendance, privacy, "time", event_organizer_id, event_type_id, id, location_id, description, name, picture) VALUES (50, 1, '2025-10-20 18:00:00', '243d38ad-8ba3-48e0-99bd-0f2e31a710be', '5de088d0-7bff-44f5-b144-51d6a24e740b', '6915ce46-d213-424b-a3c4-035767714df0', 'a7c5e2b9-d3f4-49b8-b6c1-3f9e7a4d5b2c', 'Step into a world of spooktacular glamour at Heidi Klums Halloween Party! Enjoy jaw-dropping costumes.', 'Halloween Party', '19.jpg');
INSERT INTO events (max_attendance, privacy, "time", event_organizer_id, event_type_id, id, location_id, description, name, picture) VALUES (400, 1, '2025-10-20 18:00:00', '243d38ad-8ba3-48e0-99bd-0f2e31a710be', '5de088d0-7bff-44f5-b144-51d6a24e740b', 'f1ad3604-fef5-439a-8adb-45776a019a55', 'a7c5e2b9-d3f4-49b8-b6c1-3f9e7a4d5b2c', 'Birthday party', 'Global Innovation Summit', '20.jpg');
INSERT INTO events (max_attendance, privacy, "time", event_organizer_id, event_type_id, id, location_id, description, name, picture) VALUES (800, 0, '2025-10-21 14:00:00', '57c05a8a-dd8b-4da8-af73-90961e423f42', '4e7ec0af-af4e-463b-abe9-d8c2ba317d0a', '4b3a7e9c-d8f5-49a1-b2c7-5a9d7f6e3c2b', 'a7c5e2b9-d3f4-49b8-b6c1-3f9e7a4d5b2c', 'Celebrate the season at the Spring Fair! Enjoy vibrant flower displays, artisan markets, live music,', 'Spring Fair', '21.jpg');


--
-- Data for Name: agenda_activities; Type: TABLE DATA; Schema: public; Owner: -
--

INSERT INTO agenda_activities (end_time, start_time, event_id, id, description, location_name, name) VALUES ('2024-12-07 15:30:00', '2024-12-07 14:30:00', '3f7b2c9e-4a6f-4d5b-b8c1-7a2f9e3b6d4a', 'b9a7f3d9-c5e4-4a2a-b1e8-a9f7b2c9d8f4', 'Keynote speech by the CEO', 'Main Hall', 'Opening Ceremony');
INSERT INTO agenda_activities (end_time, start_time, event_id, id, description, location_name, name) VALUES ('2024-12-07 17:00:00', '2024-12-07 16:00:00', '2d4a7c9e-6f3b-42a1-b8f5-3c7e9b6a4d5f', 'e9d2c5f7-3e7f-4a6d-b1f7-d9b5e7d2a8b9', 'Networking session with industry leaders', 'Conference Room 1', 'Networking Session');
INSERT INTO agenda_activities (end_time, start_time, event_id, id, description, location_name, name) VALUES ('2024-12-07 18:30:00', '2024-12-07 17:30:00', '4b3a7e9c-d8f5-49a1-b2c7-5a9d7f6e3c2b', 'a8e7d6f9-b5f8-42d2-9a7b-5d9f7c8d9b6a', 'Panel discussion on emerging trends in technology', 'Conference Room 2', 'Panel Discussion');


--
-- Data for Name: blocks; Type: TABLE DATA; Schema: public; Owner: -
--

INSERT INTO blocks ("timestamp", blocked_id, id, who_id) VALUES ('2024-12-07 15:00:00', '6a1c3423-e400-443f-93c1-9491b9dafb03', 'f4bb3025-88e7-4dc8-835e-6fda3279f8c8', '97d6429d-d755-4337-a2b0-31f54633c538');
INSERT INTO blocks ("timestamp", blocked_id, id, who_id) VALUES ('2024-12-07 16:00:00', 'd7e7937b-02f3-47d7-bf64-f6e1555ff828', '5db5b1b6-3a15-40b5-a8ec-72e7e62a8fbf', '97d6429d-d755-4337-a2b0-31f54633c538');
INSERT INTO blocks ("timestamp", blocked_id, id, who_id) VALUES ('2024-12-07 17:00:00', 'd7e7937b-02f3-47d7-bf64-f6e1555ff828', '9dbedc2e-4d54-4c4b-902b-49f3f1d6bda5', '6a1c3423-e400-443f-93c1-9491b9dafb03');


--
-- Data for Name: categories; Type: TABLE DATA; Schema: public; Owner: -
--

INSERT INTO categories (is_deleted, status, id, description, name) VALUES (false, 0, 'a7c5e2b9-d3f4-49b8-b6c1-3f9e7a4d5b2c', 'Suggested category', 'Suggestion');
INSERT INTO categories (is_deleted, status, id, description, name) VALUES (false, 1, 'b1d5d5c5-f6c1-4039-a8a5-4fc3ea0a4e2a', 'Category for technology', 'Technology');
INSERT INTO categories (is_deleted, status, id, description, name) VALUES (false, 1, 'd4f4e6b7-d2d5-4376-8a9b-7c4f3b3c1e7d', 'Category for arts and culture', 'Arts');
INSERT INTO categories (is_deleted, status, id, description, name) VALUES (false, 1, 'c7d2b4f3-8c4f-432e-8b5c-9a1d3f1b8d5a', 'Deprecated category', 'Legacy');


--
-- Data for Name: budget_items; Type: TABLE DATA; Schema: public; Owner: -
--

INSERT INTO budget_items (amount, category_id, event_id, id) VALUES (6000, 'd4f4e6b7-d2d5-4376-8a9b-7c4f3b3c1e7d', '2d4a7c9e-6f3b-42a1-b8f5-3c7e9b6a4d5f', 'a5f8e6b7-d2d5-4376-8a9b-7c4f3b3c1e1e');


--
-- Data for Name: category_event_types; Type: TABLE DATA; Schema: public; Owner: -
--

INSERT INTO category_event_types (category_id, event_type_id) VALUES ('c7d2b4f3-8c4f-432e-8b5c-9a1d3f1b8d5a', '4e7ec0af-af4e-463b-abe9-d8c2ba317d0a');
INSERT INTO category_event_types (category_id, event_type_id) VALUES ('b1d5d5c5-f6c1-4039-a8a5-4fc3ea0a4e2a', '4e7ec0af-af4e-463b-abe9-d8c2ba317d0a');
INSERT INTO category_event_types (category_id, event_type_id) VALUES ('d4f4e6b7-d2d5-4376-8a9b-7c4f3b3c1e7d', '5de088d0-7bff-44f5-b144-51d6a24e740b');
INSERT INTO category_event_types (category_id, event_type_id) VALUES ('d4f4e6b7-d2d5-4376-8a9b-7c4f3b3c1e7d', 'f0dcab26-9656-491e-bcce-bf23f723e998');


--
-- Data for Name: service_providers; Type: TABLE DATA; Schema: public; Owner: -
--

INSERT INTO service_providers (type, work_end, work_start, company_location_id, id, location_id, company_description, company_email, company_name, company_phone_number, name, phone_number, profile_picture, surname) VALUES (1, '18:00:00', '09:00:00', '4e2d5b9f-a6c3-49a1-b8f5-7d9c7b6e3a2c', 'a7c9e5b3-d4f2-49a1-b8c7-3e7f9a5b2c6d', 'a7c5e2b9-d3f4-49b8-b6c1-3f9e7a4d5b2c', 'Tech solutions for small businesses', 'info@techcorp.com', 'TechCorp', '1234567890', 'John', '1234567890', '', 'Doe');
INSERT INTO service_providers (type, work_end, work_start, company_location_id, id, location_id, company_description, company_email, company_name, company_phone_number, name, phone_number, profile_picture, surname) VALUES (1, '17:00:00', '08:00:00', 'b6f3e9d2-c7a5-4b1a-b2c9-8d5e7c6f3a2b', 'c7a9e5d3-f2b4-4a1b-b8c6-3f9e7a5b2d4f', 'a7c5e2b9-d3f4-49b8-b6c1-3f9e7a4d5b2c', 'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Proin posuere pulvinar est, et molestie justo tempus in. Phasellus vitae mi ac libero posuere efficitur. Donec laoreet turpis lectus, eget lacinia leo eleifend gravida.', 'contact@bizconsult.com', 'BizConsult', '1234567890', 'Alice', '9876543210', '', 'Smith');
INSERT INTO service_providers (type, work_end, work_start, company_location_id, id, location_id, company_description, company_email, company_name, company_phone_number, name, phone_number, profile_picture, surname) VALUES (1, '20:00:00', '10:00:00', 'a7c5e2b9-d3f4-49b8-b6c1-3f9e7a4d5b2c', 'd7b5e9c3-a2f4-49b8-b6c1-3f9a7e5b2c8d', '4e2d5b9f-a6c3-49a1-b8f5-7d9c7b6e3a2c', 'Offering creative solutions for marketing. Something something something something ', 'support@creativehub.com', 'CreativeHub', '1234567890', 'Bora', '1122334455', 'bora.png', 'Todorovic');


--
-- Data for Name: products; Type: TABLE DATA; Schema: public; Owner: -
--

INSERT INTO products (cancellation_window_days, duration_minutes, is_auto_accept, is_available, is_deleted, is_visible, reservation_window_days, status, edit_timestamp, category_id, id, service_provider_id, type, description, name) VALUES (NULL, NULL, NULL, true, false, true, 7, 1, '2025-12-07 09:45:00', 'd4f4e6b7-d2d5-4376-8a9b-7c4f3b3c1e7d', '11fbaacb-5d2e-44b0-8f7e-1d302baef461', 'd7b5e9c3-a2f4-49b8-b6c1-3f9a7e5b2c8d', 'PRODUCT', 'Books to display on your office desk to look smart.', 'Office Books');
INSERT INTO products (cancellation_window_days, duration_minutes, is_auto_accept, is_available, is_deleted, is_visible, reservation_window_days, status, edit_timestamp, category_id, id, service_provider_id, type, description, name) VALUES (NULL, NULL, NULL, true, false, true, 7, 1, '2025-12-07 09:45:00', 'd4f4e6b7-d2d5-4376-8a9b-7c4f3b3c1e7d', 'df9a2350-c532-4a75-9cbf-5d5ea6fc807d', 'd7b5e9c3-a2f4-49b8-b6c1-3f9a7e5b2c8d', 'PRODUCT', 'Pens to give your other employed friends', 'Office Pens');
INSERT INTO products (cancellation_window_days, duration_minutes, is_auto_accept, is_available, is_deleted, is_visible, reservation_window_days, status, edit_timestamp, category_id, id, service_provider_id, type, description, name) VALUES (NULL, NULL, NULL, true, false, true, 7, 1, '2025-12-07 09:45:00', 'd4f4e6b7-d2d5-4376-8a9b-7c4f3b3c1e7d', '1237e35c-80ff-4a2a-8245-2728cb45ee11', 'd7b5e9c3-a2f4-49b8-b6c1-3f9a7e5b2c8d', 'PRODUCT', 'Wake up the artist in you', 'Art Canvases');
INSERT INTO products (cancellation_window_days, duration_minutes, is_auto_accept, is_available, is_deleted, is_visible, reservation_window_days, status, edit_timestamp, category_id, id, service_provider_id, type, description, name) VALUES (NULL, NULL, NULL, true, false, true, 7, 1, '2025-12-07 09:45:00', 'd4f4e6b7-d2d5-4376-8a9b-7c4f3b3c1e7d', '45067d8d-9d86-4104-97cd-7054c48cbbc6', 'd7b5e9c3-a2f4-49b8-b6c1-3f9a7e5b2c8d', 'PRODUCT', 'If you dont know what these are they are then not for you.', 'Art Brushes');
INSERT INTO products (cancellation_window_days, duration_minutes, is_auto_accept, is_available, is_deleted, is_visible, reservation_window_days, status, edit_timestamp, category_id, id, service_provider_id, type, description, name) VALUES (NULL, NULL, NULL, false, false, true, 7, 0, '2025-12-07 09:45:00', 'a7c5e2b9-d3f4-49b8-b6c1-3f9e7a4d5b2c', '894d865c-0343-4a4a-9594-6ffc4f3a5a16', 'd7b5e9c3-a2f4-49b8-b6c1-3f9a7e5b2c8d', 'PRODUCT', 'Cool pencils that you are totally gonna use and not forget in a drawer', 'Art Pencils');
INSERT INTO products (cancellation_window_days, duration_minutes, is_auto_accept, is_available, is_deleted, is_visible, reservation_window_days, status, edit_timestamp, category_id, id, service_provider_id, type, description, name) VALUES (3, 120, true, true, false, true, 5, 1, '2025-12-07 09:30:00', 'b1d5d5c5-f6c1-4039-a8a5-4fc3ea0a4e2a', '935e1b52-6180-419a-bbe8-909db6cd6cbc', 'a7c9e5b3-d4f2-49a1-b8c7-3e7f9a5b2c6d', 'SERVICE', 'A professional IT consultation', 'Tech Consultation');
INSERT INTO products (cancellation_window_days, duration_minutes, is_auto_accept, is_available, is_deleted, is_visible, reservation_window_days, status, edit_timestamp, category_id, id, service_provider_id, type, description, name) VALUES (5, 180, false, true, false, true, 7, 1, '2025-12-07 09:45:00', 'd4f4e6b7-d2d5-4376-8a9b-7c4f3b3c1e7d', '314c1838-8cbe-471c-9403-dc49baad1977', 'd7b5e9c3-a2f4-49b8-b6c1-3f9a7e5b2c8d', 'SERVICE', 'A cleaning service for offices', 'Office Cleaning5');
INSERT INTO products (cancellation_window_days, duration_minutes, is_auto_accept, is_available, is_deleted, is_visible, reservation_window_days, status, edit_timestamp, category_id, id, service_provider_id, type, description, name) VALUES (10, 90, true, true, false, true, 3, 1, '2025-12-07 10:00:00', 'd4f4e6b7-d2d5-4376-8a9b-7c4f3b3c1e7d', '2eed4933-2477-487e-8b99-c39a9ac939dd', 'd7b5e9c3-a2f4-49b8-b6c1-3f9a7e5b2c8d', 'SERVICE', 'A professional cleaning service for homes', 'Home Cleaning');


--
-- Data for Name: comments; Type: TABLE DATA; Schema: public; Owner: -
--

INSERT INTO comments (status, author_id, id, product_id, content) VALUES (0, '3f2b7e9a-6d4c-4b8f-b2a1-5c7e3d9f6b2a', 'e52d2a61-abc7-42e9-82d4-7b3f52a4a1c5', '2eed4933-2477-487e-8b99-c39a9ac939dd', 'Great product!');
INSERT INTO comments (status, author_id, id, product_id, content) VALUES (0, '3f2b7e9a-6d4c-4b8f-b2a1-5c7e3d9f6b2a', 'f92c4b63-d2a1-49d7-8f1e-9b4c72e1a5f3', '2eed4933-2477-487e-8b99-c39a9ac939dd', 'Needs improvement.');
INSERT INTO comments (status, author_id, id, product_id, content) VALUES (0, '57c05a8a-dd8b-4da8-af73-90961e423f42', '3b9e6a2c-d7f2-4a3b-b2c9-7e5f4b6a1c8d', '935e1b52-6180-419a-bbe8-909db6cd6cbc', 'Highly recommended!');


--
-- Data for Name: company_pictures; Type: TABLE DATA; Schema: public; Owner: -
--

INSERT INTO company_pictures (service_provider_id, picture_url) VALUES ('a7c9e5b3-d4f2-49a1-b8c7-3e7f9a5b2c6d', '11.jpg');
INSERT INTO company_pictures (service_provider_id, picture_url) VALUES ('c7a9e5d3-f2b4-4a1b-b8c6-3f9e7a5b2d4f', '12.jpg');
INSERT INTO company_pictures (service_provider_id, picture_url) VALUES ('c7a9e5d3-f2b4-4a1b-b8c6-3f9e7a5b2d4f', '13.jpg');
INSERT INTO company_pictures (service_provider_id, picture_url) VALUES ('d7b5e9c3-a2f4-49b8-b6c1-3f9a7e5b2c8d', '14.jpg');
INSERT INTO company_pictures (service_provider_id, picture_url) VALUES ('d7b5e9c3-a2f4-49b8-b6c1-3f9a7e5b2c8d', 'company1.png');
INSERT INTO company_pictures (service_provider_id, picture_url) VALUES ('d7b5e9c3-a2f4-49b8-b6c1-3f9a7e5b2c8d', 'company2.png');


--
-- Data for Name: event_ratings; Type: TABLE DATA; Schema: public; Owner: -
--

INSERT INTO event_ratings (value, "timestamp", account_id, event_id, id) VALUES (1, '2023-05-02 10:30:00', '97d6429d-d755-4337-a2b0-31f54633c538', '3f7b2c9e-4a6f-4d5b-b8c1-7a2f9e3b6d4a', '8fbd8fbb-1095-4f4f-9c58-0e2c123f9fbb');
INSERT INTO event_ratings (value, "timestamp", account_id, event_id, id) VALUES (5, '2023-05-30 10:30:00', 'd7e7937b-02f3-47d7-bf64-f6e1555ff828', '3f7b2c9e-4a6f-4d5b-b8c1-7a2f9e3b6d4a', '4d57fa92-e4b7-442a-b7f7-4b6c5190623e');
INSERT INTO event_ratings (value, "timestamp", account_id, event_id, id) VALUES (5, '2023-07-01 10:30:00', '6a1c3423-e400-443f-93c1-9491b9dafb03', '3f7b2c9e-4a6f-4d5b-b8c1-7a2f9e3b6d4a', '9a1a3a1e-6e62-4f89-9d7c-9e1c94a2117e');
INSERT INTO event_ratings (value, "timestamp", account_id, event_id, id) VALUES (2, '2023-09-20 10:30:00', 'abd3ba1c-76a1-44c5-8204-6cbbcfbfda5d', '3f7b2c9e-4a6f-4d5b-b8c1-7a2f9e3b6d4a', 'cf9aee7c-68cc-4a1a-b86d-c6e98de6c426');
INSERT INTO event_ratings (value, "timestamp", account_id, event_id, id) VALUES (5, '2024-02-01 10:30:00', '1b02e7df-ea2a-4cb2-b93b-a067a7f12fc6', '3f7b2c9e-4a6f-4d5b-b8c1-7a2f9e3b6d4a', 'f5f65e0e-39fa-4c1f-a3e7-46f3e8b83c9e');
INSERT INTO event_ratings (value, "timestamp", account_id, event_id, id) VALUES (5, '2024-07-01 10:30:00', 'ae7adea0-516c-47b5-ae7b-10abcb32752d', '3f7b2c9e-4a6f-4d5b-b8c1-7a2f9e3b6d4a', 'daeb6b7a-93b6-4b1b-a1bb-f60d7283e27b');
INSERT INTO event_ratings (value, "timestamp", account_id, event_id, id) VALUES (3, '2024-11-01 10:30:00', '49a1dae3-323c-460b-bbcd-0fc1132e6bb1', '3f7b2c9e-4a6f-4d5b-b8c1-7a2f9e3b6d4a', 'b67e2fa4-7b19-4f7a-a84d-4b9c263fbf44');
INSERT INTO event_ratings (value, "timestamp", account_id, event_id, id) VALUES (1, '2025-03-11 10:30:00', 'e527b2da-66ae-4076-8301-d3a9dc3c5860', '3f7b2c9e-4a6f-4d5b-b8c1-7a2f9e3b6d4a', '8eb8bc31-1b42-4d07-9bc5-2dca51d73a54');
INSERT INTO event_ratings (value, "timestamp", account_id, event_id, id) VALUES (1, '2023-05-02 10:30:00', '97d6429d-d755-4337-a2b0-31f54633c538', '2d4a7c9e-6f3b-42a1-b8f5-3c7e9b6a4d5f', '4f247d42-9b0d-4f7e-88cc-82b53e7e06ea');
INSERT INTO event_ratings (value, "timestamp", account_id, event_id, id) VALUES (5, '2023-05-30 10:30:00', 'd7e7937b-02f3-47d7-bf64-f6e1555ff828', '2d4a7c9e-6f3b-42a1-b8f5-3c7e9b6a4d5f', '0d0b755f-1576-4b8b-9e12-07aaae25d2ec');
INSERT INTO event_ratings (value, "timestamp", account_id, event_id, id) VALUES (5, '2023-07-01 10:30:00', '6a1c3423-e400-443f-93c1-9491b9dafb03', '2d4a7c9e-6f3b-42a1-b8f5-3c7e9b6a4d5f', '93c83675-6455-4ca0-a637-9b7c5ac3df93');
INSERT INTO event_ratings (value, "timestamp", account_id, event_id, id) VALUES (2, '2023-09-20 10:30:00', 'abd3ba1c-76a1-44c5-8204-6cbbcfbfda5d', '2d4a7c9e-6f3b-42a1-b8f5-3c7e9b6a4d5f', '24342c4a-09bc-4b20-97aa-862a12a6ae19');
INSERT INTO event_ratings (value, "timestamp", account_id, event_id, id) VALUES (5, '2024-02-01 10:30:00', '1b02e7df-ea2a-4cb2-b93b-a067a7f12fc6', '2d4a7c9e-6f3b-42a1-b8f5-3c7e9b6a4d5f', 'ce1f4767-89a1-4cc9-95f7-19a5f993ab1f');
INSERT INTO event_ratings (value, "timestamp", account_id, event_id, id) VALUES (5, '2024-07-01 10:30:00', 'ae7adea0-516c-47b5-ae7b-10abcb32752d', '2d4a7c9e-6f3b-42a1-b8f5-3c7e9b6a4d5f', 'b255ef62-f4e4-4a0e-bd87-b9e00ec0b731');
INSERT INTO event_ratings (value, "timestamp", account_id, event_id, id) VALUES (3, '2024-11-01 10:30:00', '49a1dae3-323c-460b-bbcd-0fc1132e6bb1', '2d4a7c9e-6f3b-42a1-b8f5-3c7e9b6a4d5f', '8155e3a0-fd9e-4ad0-b2ec-e58fa361f46c');
INSERT INTO event_ratings (value, "timestamp", account_id, event_id, id) VALUES (1, '2025-03-11 10:30:00', 'e527b2da-66ae-4076-8301-d3a9dc3c5860', '2d4a7c9e-6f3b-42a1-b8f5-3c7e9b6a4d5f', 'd79d95e5-c8e9-48cd-8f06-066ae02c636b');
INSERT INTO event_ratings (value, "timestamp", account_id, event_id, id) VALUES (1, '2023-05-02 10:30:00', '97d6429d-d755-4337-a2b0-31f54633c538', '8c216a1f-6d65-4256-95e0-6a820d5fb902', 'b3d7d9af-58f5-41d2-ae6e-6d10bb04e0f4');
INSERT INTO event_ratings (value, "timestamp", account_id, event_id, id) VALUES (5, '2023-05-30 10:30:00', 'd7e7937b-02f3-47d7-bf64-f6e1555ff828', '8c216a1f-6d65-4256-95e0-6a820d5fb902', '70cfa8d1-5a5f-41a0-90a6-9c565c4427d0');
INSERT INTO event_ratings (value, "timestamp", account_id, event_id, id) VALUES (5, '2023-07-01 10:30:00', '6a1c3423-e400-443f-93c1-9491b9dafb03', '8c216a1f-6d65-4256-95e0-6a820d5fb902', 'f8fce153-5ea4-4f33-9a9d-29e42b9ce228');
INSERT INTO event_ratings (value, "timestamp", account_id, event_id, id) VALUES (2, '2023-09-20 10:30:00', 'abd3ba1c-76a1-44c5-8204-6cbbcfbfda5d', '8c216a1f-6d65-4256-95e0-6a820d5fb902', '3752d1a9-03ce-4d6c-9d33-c82122e2b3f3');
INSERT INTO event_ratings (value, "timestamp", account_id, event_id, id) VALUES (5, '2024-02-01 10:30:00', '1b02e7df-ea2a-4cb2-b93b-a067a7f12fc6', '8c216a1f-6d65-4256-95e0-6a820d5fb902', 'd5a5d36e-9c3f-4df3-8a87-9b2d949ecb67');
INSERT INTO event_ratings (value, "timestamp", account_id, event_id, id) VALUES (5, '2024-07-01 10:30:00', 'ae7adea0-516c-47b5-ae7b-10abcb32752d', '8c216a1f-6d65-4256-95e0-6a820d5fb902', '0aabb2d0-3f23-463d-8a8c-c2a58a6b34b6');
INSERT INTO event_ratings (value, "timestamp", account_id, event_id, id) VALUES (3, '2024-11-01 10:30:00', '49a1dae3-323c-460b-bbcd-0fc1132e6bb1', '8c216a1f-6d65-4256-95e0-6a820d5fb902', '5922efb3-3c07-4d19-9e67-df31d0b8f7b9');
INSERT INTO event_ratings (value, "timestamp", account_id, event_id, id) VALUES (1, '2025-03-11 10:30:00', 'e527b2da-66ae-4076-8301-d3a9dc3c5860', '8c216a1f-6d65-4256-95e0-6a820d5fb902', '68a45f13-2563-4f48-b8e3-8a7f1bc74fcd');
INSERT INTO event_ratings (value, "timestamp", account_id, event_id, id) VALUES (1, '2023-05-02 10:30:00', '97d6429d-d755-4337-a2b0-31f54633c538', '06aee816-a4ec-4d3c-9d9c-2c0f6bb96285', '113e21f1-d0bc-4b29-bfef-2f907b91dd51');
INSERT INTO event_ratings (value, "timestamp", account_id, event_id, id) VALUES (5, '2023-05-30 10:30:00', 'd7e7937b-02f3-47d7-bf64-f6e1555ff828', '06aee816-a4ec-4d3c-9d9c-2c0f6bb96285', 'b592e38d-9d09-4b27-bf8e-1cae4cd79d14');
INSERT INTO event_ratings (value, "timestamp", account_id, event_id, id) VALUES (5, '2023-07-01 10:30:00', '6a1c3423-e400-443f-93c1-9491b9dafb03', '06aee816-a4ec-4d3c-9d9c-2c0f6bb96285', '0f0204c3-9d21-41c3-947a-1b979f254a5b');
INSERT INTO event_ratings (value, "timestamp", account_id, event_id, id) VALUES (2, '2023-09-20 10:30:00', 'abd3ba1c-76a1-44c5-8204-6cbbcfbfda5d', '06aee816-a4ec-4d3c-9d9c-2c0f6bb96285', '4de567d7-0423-4f22-81aa-2eacfa6e3d71');
INSERT INTO event_ratings (value, "timestamp", account_id, event_id, id) VALUES (5, '2024-02-01 10:30:00', '1b02e7df-ea2a-4cb2-b93b-a067a7f12fc6', '06aee816-a4ec-4d3c-9d9c-2c0f6bb96285', '6badef61-9b84-4e89-ace0-9e651a3991d8');
INSERT INTO event_ratings (value, "timestamp", account_id, event_id, id) VALUES (5, '2024-07-01 10:30:00', 'ae7adea0-516c-47b5-ae7b-10abcb32752d', '06aee816-a4ec-4d3c-9d9c-2c0f6bb96285', '470a9e07-60d2-4238-b12e-f2a8f2b5e7ea');
INSERT INTO event_ratings (value, "timestamp", account_id, event_id, id) VALUES (3, '2024-11-01 10:30:00', '49a1dae3-323c-460b-bbcd-0fc1132e6bb1', '06aee816-a4ec-4d3c-9d9c-2c0f6bb96285', 'd1e168bb-9c16-4b21-8ff7-2e1783a9e29e');
INSERT INTO event_ratings (value, "timestamp", account_id, event_id, id) VALUES (1, '2025-03-11 10:30:00', 'e527b2da-66ae-4076-8301-d3a9dc3c5860', '06aee816-a4ec-4d3c-9d9c-2c0f6bb96285', '5f77e6cc-2a47-4dff-a4b1-6e6c87150f08');
INSERT INTO event_ratings (value, "timestamp", account_id, event_id, id) VALUES (1, '2023-05-02 10:30:00', '97d6429d-d755-4337-a2b0-31f54633c538', '6915ce46-d213-424b-a3c4-035767714df0', '49fa9f94-575a-4b6c-8d64-c547ca47078e');
INSERT INTO event_ratings (value, "timestamp", account_id, event_id, id) VALUES (5, '2023-05-30 10:30:00', 'd7e7937b-02f3-47d7-bf64-f6e1555ff828', '6915ce46-d213-424b-a3c4-035767714df0', 'db69c83c-f06c-4b29-9a6c-1b8d1b33f658');
INSERT INTO event_ratings (value, "timestamp", account_id, event_id, id) VALUES (5, '2023-07-01 10:30:00', '6a1c3423-e400-443f-93c1-9491b9dafb03', '6915ce46-d213-424b-a3c4-035767714df0', 'f8dcb4b9-0f68-4466-98d4-e121d3bf3682');
INSERT INTO event_ratings (value, "timestamp", account_id, event_id, id) VALUES (2, '2023-09-20 10:30:00', 'abd3ba1c-76a1-44c5-8204-6cbbcfbfda5d', '6915ce46-d213-424b-a3c4-035767714df0', 'a1f0eebc-b1fc-4b28-8f9f-82d7f1a2f2a0');
INSERT INTO event_ratings (value, "timestamp", account_id, event_id, id) VALUES (5, '2024-02-01 10:30:00', '1b02e7df-ea2a-4cb2-b93b-a067a7f12fc6', '6915ce46-d213-424b-a3c4-035767714df0', '4b8c1ac8-0956-4e91-a3be-8f52d4c9d14e');
INSERT INTO event_ratings (value, "timestamp", account_id, event_id, id) VALUES (5, '2024-07-01 10:30:00', 'ae7adea0-516c-47b5-ae7b-10abcb32752d', '6915ce46-d213-424b-a3c4-035767714df0', '02f865b6-40f2-4033-8b7d-ff4a1f679e36');
INSERT INTO event_ratings (value, "timestamp", account_id, event_id, id) VALUES (3, '2024-11-01 10:30:00', '49a1dae3-323c-460b-bbcd-0fc1132e6bb1', '6915ce46-d213-424b-a3c4-035767714df0', 'db3b6e0b-6ef7-4c9e-87a6-7f15c31dff1f');
INSERT INTO event_ratings (value, "timestamp", account_id, event_id, id) VALUES (1, '2025-03-11 10:30:00', 'e527b2da-66ae-4076-8301-d3a9dc3c5860', '6915ce46-d213-424b-a3c4-035767714df0', '87e89d0a-6d87-4ef3-9999-3b5f5b372e3b');
INSERT INTO event_ratings (value, "timestamp", account_id, event_id, id) VALUES (1, '2023-05-02 10:30:00', '97d6429d-d755-4337-a2b0-31f54633c538', 'f1ad3604-fef5-439a-8adb-45776a019a55', '0c92f843-58b8-4620-ae06-b0d1e49b3c63');
INSERT INTO event_ratings (value, "timestamp", account_id, event_id, id) VALUES (5, '2023-05-30 10:30:00', 'd7e7937b-02f3-47d7-bf64-f6e1555ff828', 'f1ad3604-fef5-439a-8adb-45776a019a55', 'f86c45e4-23e9-42f3-bd24-72877e8c081b');
INSERT INTO event_ratings (value, "timestamp", account_id, event_id, id) VALUES (5, '2023-07-01 10:30:00', '6a1c3423-e400-443f-93c1-9491b9dafb03', 'f1ad3604-fef5-439a-8adb-45776a019a55', 'a0ddc0bc-1f3f-4c6a-9cc2-d2691caa9a8c');
INSERT INTO event_ratings (value, "timestamp", account_id, event_id, id) VALUES (2, '2023-09-20 10:30:00', 'abd3ba1c-76a1-44c5-8204-6cbbcfbfda5d', 'f1ad3604-fef5-439a-8adb-45776a019a55', 'e27bbd09-2c9f-4324-9650-27df8c2c99d4');
INSERT INTO event_ratings (value, "timestamp", account_id, event_id, id) VALUES (5, '2024-02-01 10:30:00', '1b02e7df-ea2a-4cb2-b93b-a067a7f12fc6', 'f1ad3604-fef5-439a-8adb-45776a019a55', '4c2bf65c-73a2-44cc-a4ec-e5f703fd07d1');
INSERT INTO event_ratings (value, "timestamp", account_id, event_id, id) VALUES (5, '2024-07-01 10:30:00', 'ae7adea0-516c-47b5-ae7b-10abcb32752d', 'f1ad3604-fef5-439a-8adb-45776a019a55', 'ebda8707-1e1c-4fc2-819d-c0b5e1eac788');
INSERT INTO event_ratings (value, "timestamp", account_id, event_id, id) VALUES (3, '2024-11-01 10:30:00', '49a1dae3-323c-460b-bbcd-0fc1132e6bb1', 'f1ad3604-fef5-439a-8adb-45776a019a55', '2f87e6cd-ec1e-4f01-9a78-c570b2f2a3ea');
INSERT INTO event_ratings (value, "timestamp", account_id, event_id, id) VALUES (1, '2025-03-11 10:30:00', 'e527b2da-66ae-4076-8301-d3a9dc3c5860', 'f1ad3604-fef5-439a-8adb-45776a019a55', 'e741bfc8-262f-4c6b-9cc7-3d5ce331fb41');
INSERT INTO event_ratings (value, "timestamp", account_id, event_id, id) VALUES (1, '2023-05-02 10:30:00', '97d6429d-d755-4337-a2b0-31f54633c538', '4b3a7e9c-d8f5-49a1-b2c7-5a9d7f6e3c2b', 'a5cfa36f-81fd-4b4e-bdf8-97d875431688');
INSERT INTO event_ratings (value, "timestamp", account_id, event_id, id) VALUES (5, '2023-05-30 10:30:00', 'd7e7937b-02f3-47d7-bf64-f6e1555ff828', '4b3a7e9c-d8f5-49a1-b2c7-5a9d7f6e3c2b', '44d1e1ae-e7b7-4c76-9fcf-e6e1a9a57e42');
INSERT INTO event_ratings (value, "timestamp", account_id, event_id, id) VALUES (5, '2023-07-01 10:30:00', '6a1c3423-e400-443f-93c1-9491b9dafb03', '4b3a7e9c-d8f5-49a1-b2c7-5a9d7f6e3c2b', '7b1891b7-f0ee-4644-bb88-3ddfbf1170a5');
INSERT INTO event_ratings (value, "timestamp", account_id, event_id, id) VALUES (2, '2023-09-20 10:30:00', 'abd3ba1c-76a1-44c5-8204-6cbbcfbfda5d', '4b3a7e9c-d8f5-49a1-b2c7-5a9d7f6e3c2b', '1de5bcb0-d9f3-46fa-9ea0-0e99c612901e');
INSERT INTO event_ratings (value, "timestamp", account_id, event_id, id) VALUES (5, '2024-02-01 10:30:00', '1b02e7df-ea2a-4cb2-b93b-a067a7f12fc6', '4b3a7e9c-d8f5-49a1-b2c7-5a9d7f6e3c2b', '99cfeada-c25a-465c-87cc-6e073fcf64e2');
INSERT INTO event_ratings (value, "timestamp", account_id, event_id, id) VALUES (5, '2024-07-01 10:30:00', 'ae7adea0-516c-47b5-ae7b-10abcb32752d', '4b3a7e9c-d8f5-49a1-b2c7-5a9d7f6e3c2b', 'c0488a7c-f5cf-4c33-bdbb-4b6f3cb21c5f');
INSERT INTO event_ratings (value, "timestamp", account_id, event_id, id) VALUES (3, '2024-11-01 10:30:00', '49a1dae3-323c-460b-bbcd-0fc1132e6bb1', '4b3a7e9c-d8f5-49a1-b2c7-5a9d7f6e3c2b', '278f69f5-6b9f-4b5e-a00a-506dfeb878e1');
INSERT INTO event_ratings (value, "timestamp", account_id, event_id, id) VALUES (1, '2025-03-11 10:30:00', 'e527b2da-66ae-4076-8301-d3a9dc3c5860', '4b3a7e9c-d8f5-49a1-b2c7-5a9d7f6e3c2b', '8a5e6804-1228-4a2c-9f21-b12d470c4455');


--
-- Data for Name: invitations; Type: TABLE DATA; Schema: public; Owner: -
--

INSERT INTO invitations (status, "timestamp", event_id, id, picture, target_email) VALUES (0, '2024-12-08 12:00:00', '3f7b2c9e-4a6f-4d5b-b8c1-7a2f9e3b6d4a', 'f5b2d3c9-7e6a-49a1-b8c5-3e7f9d2a4b6c', 'https://example.com/invitation1.jpg', 'vanjakostic03@gmail.com');
INSERT INTO invitations (status, "timestamp", event_id, id, picture, target_email) VALUES (1, '2024-12-09 15:30:00', '2d4a7c9e-6f3b-42a1-b8f5-3c7e9b6a4d5f', '7f2a3b9e-6d4c-4b8f-b2a1-5c7e3d9f6b2a', 'https://example.com/invitation2.jpg', 'attendee2@example.com');
INSERT INTO invitations (status, "timestamp", event_id, id, picture, target_email) VALUES (2, '2024-12-10 20:00:00', '4b3a7e9c-d8f5-49a1-b2c7-5a9d7f6e3c2b', '3c9a7b2e-4d5f-4a8b-b1c7-5f3e2d9b6a4c', 'https://example.com/invitation3.jpg', 'attendee3@example.com');
INSERT INTO invitations (status, "timestamp", event_id, id, picture, target_email) VALUES (0, '2024-12-09 15:30:00', '3f7b2c9e-4a6f-4d5b-b8c1-7a2f9e3b6d4a', '3f4c5e78-9a0b-4e72-91d2-13c40f672812', 'https://example.com/invitation2.jpg', 'organizer1@example.com');
INSERT INTO invitations (status, "timestamp", event_id, id, picture, target_email) VALUES (1, '2024-12-09 15:30:00', '3f7b2c9e-4a6f-4d5b-b8c1-7a2f9e3b6d4a', 'a19e0b3f-4f89-44c7-8d2b-bc4f9164a524', 'https://example.com/invitation2.jpg', 'organizer3@example.com');
INSERT INTO invitations (status, "timestamp", event_id, id, picture, target_email) VALUES (1, '2024-12-09 15:30:00', '3f7b2c9e-4a6f-4d5b-b8c1-7a2f9e3b6d4a', '07f8c1a6-e611-48a5-b147-9b4ffb9ac2fa', 'https://example.com/invitation2.jpg', 'pup3@example.com');
INSERT INTO invitations (status, "timestamp", event_id, id, picture, target_email) VALUES (1, '2024-12-09 15:30:00', '3f7b2c9e-4a6f-4d5b-b8c1-7a2f9e3b6d4a', 'd2a3287f-14aa-4a29-a8ff-c63c45e60c13', 'https://example.com/invitation2.jpg', 'admin@example.com');


--
-- Data for Name: messages; Type: TABLE DATA; Schema: public; Owner: -
--

INSERT INTO messages ("timestamp", from_id, id, to_id, content) VALUES ('2024-12-07 09:00:00', '31548d6b-019f-492e-ba39-07be7a1433e5', 'b2c9e3a5-d4f7-4b8f-b7e1-3f9c6a2d5b7e', '6a1c3423-e400-443f-93c1-9491b9dafb03', 'Hello, how are you?');
INSERT INTO messages ("timestamp", from_id, id, to_id, content) VALUES ('2024-12-07 09:05:00', '6a1c3423-e400-443f-93c1-9491b9dafb03', 'e5c7b3a9-d2f6-4a1a-b8c9-7f3e6b2d9a4f', '31548d6b-019f-492e-ba39-07be7a1433e5', 'I am good, thank you!');
INSERT INTO messages ("timestamp", from_id, id, to_id, content) VALUES ('2024-12-07 09:10:00', '31548d6b-019f-492e-ba39-07be7a1433e5', '7e5a3c9d-48f1-432a-b6c1-3e7a8f5b2d9e', '6a1c3423-e400-443f-93c1-9491b9dafb03', 'See you at the event.');


--
-- Data for Name: notifications; Type: TABLE DATA; Schema: public; Owner: -
--

INSERT INTO notifications ("timestamp", event_id, id, product_id, content) VALUES ('2024-12-07 08:00:00', '3f7b2c9e-4a6f-4d5b-b8c1-7a2f9e3b6d4a', 'c7a5f4b9-d2e3-4a1a-b6f5-3c9e8d7b2a4c', NULL, 'Event reminder: Tech Meetup');
INSERT INTO notifications ("timestamp", event_id, id, product_id, content) VALUES ('2024-12-07 08:15:00', NULL, '7c5f3e9a-d8b1-4a2c-b9f6-5a3e7d2b8c4f', '2eed4933-2477-487e-8b99-c39a9ac939dd', 'New product available: Widget X');
INSERT INTO notifications ("timestamp", event_id, id, product_id, content) VALUES ('2024-12-07 08:30:00', '4b3a7e9c-d8f5-49a1-b2c7-5a9d7f6e3c2b', '3c7f9e5b-6a2d-42a9-b8c5-7d2f3b9e6a4f', NULL, 'Event cancelled: Spring Fair');


--
-- Data for Name: persons; Type: TABLE DATA; Schema: public; Owner: -
--

INSERT INTO persons (type, id, location_id, name, phone_number, profile_picture, surname) VALUES (3, 'd7b9e5c3-a6f4-49a2-b8c1-7e3f9a2d6b4f', 'a7c5e2b9-d3f4-49b8-b6c1-3f9e7a4d5b2c', 'John', '1234567890', '', 'Doe');
INSERT INTO persons (type, id, location_id, name, phone_number, profile_picture, surname) VALUES (3, '4b9c7f5a-d3e2-42a1-b6c8-3f7e9d5a2c6f', 'b6f3e9d2-c7a5-4b1a-b2c9-8d5e7c6f3a2b', 'Alice', '9876543210', '', 'Smith');
INSERT INTO persons (type, id, location_id, name, phone_number, profile_picture, surname) VALUES (3, 'b7c9e2d4-a5b3-49c8-b2f7-d4a1c7e6f2d3', '4e2d5b9f-a6c3-49a1-b8f5-7d9c7b6e3a2c', 'Emma', '1122334455', '', 'Brown');
INSERT INTO persons (type, id, location_id, name, phone_number, profile_picture, surname) VALUES (0, '20343059-c4dd-4608-81aa-104f415e8085', '4e2d5b9f-a6c3-49a1-b8f5-7d9c7b6e3a2c', 'Lane', '1122334455', 'lane.jpg', 'Gutovic');


--
-- Data for Name: persons_attending_events; Type: TABLE DATA; Schema: public; Owner: -
--

INSERT INTO persons_attending_events (event_id, person_id) VALUES ('3f7b2c9e-4a6f-4d5b-b8c1-7a2f9e3b6d4a', 'abd3ba1c-76a1-44c5-8204-6cbbcfbfda5d');
INSERT INTO persons_attending_events (event_id, person_id) VALUES ('2d4a7c9e-6f3b-42a1-b8f5-3c7e9b6a4d5f', 'abd3ba1c-76a1-44c5-8204-6cbbcfbfda5d');
INSERT INTO persons_attending_events (event_id, person_id) VALUES ('8c216a1f-6d65-4256-95e0-6a820d5fb902', 'abd3ba1c-76a1-44c5-8204-6cbbcfbfda5d');
INSERT INTO persons_attending_events (event_id, person_id) VALUES ('06aee816-a4ec-4d3c-9d9c-2c0f6bb96285', 'abd3ba1c-76a1-44c5-8204-6cbbcfbfda5d');
INSERT INTO persons_attending_events (event_id, person_id) VALUES ('6915ce46-d213-424b-a3c4-035767714df0', 'abd3ba1c-76a1-44c5-8204-6cbbcfbfda5d');
INSERT INTO persons_attending_events (event_id, person_id) VALUES ('f1ad3604-fef5-439a-8adb-45776a019a55', 'abd3ba1c-76a1-44c5-8204-6cbbcfbfda5d');
INSERT INTO persons_attending_events (event_id, person_id) VALUES ('3f7b2c9e-4a6f-4d5b-b8c1-7a2f9e3b6d4a', 'd7b9e5c3-a6f4-49a2-b8c1-7e3f9a2d6b4f');
INSERT INTO persons_attending_events (event_id, person_id) VALUES ('2d4a7c9e-6f3b-42a1-b8f5-3c7e9b6a4d5f', '4b9c7f5a-d3e2-42a1-b6c8-3f7e9d5a2c6f');
INSERT INTO persons_attending_events (event_id, person_id) VALUES ('3f7b2c9e-4a6f-4d5b-b8c1-7a2f9e3b6d4a', 'd7b5e9c3-a2f4-49b8-b6c1-3f9a7e5b2c8d');
INSERT INTO persons_attending_events (event_id, person_id) VALUES ('8c216a1f-6d65-4256-95e0-6a820d5fb902', 'd7b5e9c3-a2f4-49b8-b6c1-3f9a7e5b2c8d');
INSERT INTO persons_attending_events (event_id, person_id) VALUES ('3f7b2c9e-4a6f-4d5b-b8c1-7a2f9e3b6d4a', '57c05a8a-dd8b-4da8-af73-90961e423f42');
INSERT INTO persons_attending_events (event_id, person_id) VALUES ('8c216a1f-6d65-4256-95e0-6a820d5fb902', '57c05a8a-dd8b-4da8-af73-90961e423f42');
INSERT INTO persons_attending_events (event_id, person_id) VALUES ('3f7b2c9e-4a6f-4d5b-b8c1-7a2f9e3b6d4a', '31548d6b-019f-492e-ba39-07be7a1433e5');
INSERT INTO persons_attending_events (event_id, person_id) VALUES ('2d4a7c9e-6f3b-42a1-b8f5-3c7e9b6a4d5f', '31548d6b-019f-492e-ba39-07be7a1433e5');
INSERT INTO persons_attending_events (event_id, person_id) VALUES ('8c216a1f-6d65-4256-95e0-6a820d5fb902', '31548d6b-019f-492e-ba39-07be7a1433e5');
INSERT INTO persons_attending_events (event_id, person_id) VALUES ('4b3a7e9c-d8f5-49a1-b2c7-5a9d7f6e3c2b', 'b7c9e2d4-a5b3-49c8-b2f7-d4a1c7e6f2d3');


--
-- Data for Name: persons_favorite_events; Type: TABLE DATA; Schema: public; Owner: -
--

INSERT INTO persons_favorite_events (event_id, person_id) VALUES ('3f7b2c9e-4a6f-4d5b-b8c1-7a2f9e3b6d4a', 'd7b9e5c3-a6f4-49a2-b8c1-7e3f9a2d6b4f');
INSERT INTO persons_favorite_events (event_id, person_id) VALUES ('2d4a7c9e-6f3b-42a1-b8f5-3c7e9b6a4d5f', '4b9c7f5a-d3e2-42a1-b6c8-3f7e9d5a2c6f');
INSERT INTO persons_favorite_events (event_id, person_id) VALUES ('2d4a7c9e-6f3b-42a1-b8f5-3c7e9b6a4d5f', 'd7b5e9c3-a2f4-49b8-b6c1-3f9a7e5b2c8d');
INSERT INTO persons_favorite_events (event_id, person_id) VALUES ('3f7b2c9e-4a6f-4d5b-b8c1-7a2f9e3b6d4a', 'd7b5e9c3-a2f4-49b8-b6c1-3f9a7e5b2c8d');
INSERT INTO persons_favorite_events (event_id, person_id) VALUES ('4b3a7e9c-d8f5-49a1-b2c7-5a9d7f6e3c2b', 'd7b5e9c3-a2f4-49b8-b6c1-3f9a7e5b2c8d');
INSERT INTO persons_favorite_events (event_id, person_id) VALUES ('8c216a1f-6d65-4256-95e0-6a820d5fb902', 'd7b5e9c3-a2f4-49b8-b6c1-3f9a7e5b2c8d');
INSERT INTO persons_favorite_events (event_id, person_id) VALUES ('06aee816-a4ec-4d3c-9d9c-2c0f6bb96285', 'd7b5e9c3-a2f4-49b8-b6c1-3f9a7e5b2c8d');
INSERT INTO persons_favorite_events (event_id, person_id) VALUES ('6915ce46-d213-424b-a3c4-035767714df0', 'd7b5e9c3-a2f4-49b8-b6c1-3f9a7e5b2c8d');
INSERT INTO persons_favorite_events (event_id, person_id) VALUES ('f1ad3604-fef5-439a-8adb-45776a019a55', 'd7b5e9c3-a2f4-49b8-b6c1-3f9a7e5b2c8d');
INSERT INTO persons_favorite_events (event_id, person_id) VALUES ('4b3a7e9c-d8f5-49a1-b2c7-5a9d7f6e3c2b', 'b7c9e2d4-a5b3-49c8-b2f7-d4a1c7e6f2d3');
INSERT INTO persons_favorite_events (event_id, person_id) VALUES ('4b3a7e9c-d8f5-49a1-b2c7-5a9d7f6e3c2b', '20343059-c4dd-4608-81aa-104f415e8085');
INSERT INTO persons_favorite_events (event_id, person_id) VALUES ('f1ad3604-fef5-439a-8adb-45776a019a55', '20343059-c4dd-4608-81aa-104f415e8085');
INSERT INTO persons_favorite_events (event_id, person_id) VALUES ('8c216a1f-6d65-4256-95e0-6a820d5fb902', '20343059-c4dd-4608-81aa-104f415e8085');


--
-- Data for Name: persons_favorite_products; Type: TABLE DATA; Schema: public; Owner: -
--

INSERT INTO persons_favorite_products (person_id, product_id) VALUES ('d7b9e5c3-a6f4-49a2-b8c1-7e3f9a2d6b4f', '2eed4933-2477-487e-8b99-c39a9ac939dd');
INSERT INTO persons_favorite_products (person_id, product_id) VALUES ('4b9c7f5a-d3e2-42a1-b6c8-3f7e9d5a2c6f', '11fbaacb-5d2e-44b0-8f7e-1d302baef461');
INSERT INTO persons_favorite_products (person_id, product_id) VALUES ('d7b5e9c3-a2f4-49b8-b6c1-3f9a7e5b2c8d', '2eed4933-2477-487e-8b99-c39a9ac939dd');
INSERT INTO persons_favorite_products (person_id, product_id) VALUES ('d7b5e9c3-a2f4-49b8-b6c1-3f9a7e5b2c8d', '11fbaacb-5d2e-44b0-8f7e-1d302baef461');
INSERT INTO persons_favorite_products (person_id, product_id) VALUES ('d7b5e9c3-a2f4-49b8-b6c1-3f9a7e5b2c8d', '1237e35c-80ff-4a2a-8245-2728cb45ee11');
INSERT INTO persons_favorite_products (person_id, product_id) VALUES ('d7b5e9c3-a2f4-49b8-b6c1-3f9a7e5b2c8d', '314c1838-8cbe-471c-9403-dc49baad1977');
INSERT INTO persons_favorite_products (person_id, product_id) VALUES ('d7b5e9c3-a2f4-49b8-b6c1-3f9a7e5b2c8d', '935e1b52-6180-419a-bbe8-909db6cd6cbc');
INSERT INTO persons_favorite_products (person_id, product_id) VALUES ('d7b5e9c3-a2f4-49b8-b6c1-3f9a7e5b2c8d', 'df9a2350-c532-4a75-9cbf-5d5ea6fc807d');
INSERT INTO persons_favorite_products (person_id, product_id) VALUES ('b7c9e2d4-a5b3-49c8-b2f7-d4a1c7e6f2d3', '935e1b52-6180-419a-bbe8-909db6cd6cbc');


--
-- Data for Name: persons_notifications; Type: TABLE DATA; Schema: public; Owner: -
--

INSERT INTO persons_notifications (notification_id, person_id) VALUES ('c7a5f4b9-d2e3-4a1a-b6f5-3c9e8d7b2a4c', 'd7b9e5c3-a6f4-49a2-b8c1-7e3f9a2d6b4f');
INSERT INTO persons_notifications (notification_id, person_id) VALUES ('7c5f3e9a-d8b1-4a2c-b9f6-5a3e7d2b8c4f', '4b9c7f5a-d3e2-42a1-b6c8-3f7e9d5a2c6f');
INSERT INTO persons_notifications (notification_id, person_id) VALUES ('3c7f9e5b-6a2d-42a9-b8c5-7d2f3b9e6a4f', 'b7c9e2d4-a5b3-49c8-b2f7-d4a1c7e6f2d3');


--
-- Data for Name: prices; Type: TABLE DATA; Schema: public; Owner: -
--

INSERT INTO prices (base_price, discount, final_price, "timestamp", id) VALUES (100, 10, 90, '2024-12-07 07:30:00', '5d2f4a3b-c7a9-4e1a-b8f6-3e7a5c9d2b4f');
INSERT INTO prices (base_price, discount, final_price, "timestamp", id) VALUES (200, 20, 160, '2024-12-07 07:45:00', 'c7e5a3b9-d2f4-49b8-b1c9-5f7a3d9e6b4c');
INSERT INTO prices (base_price, discount, final_price, "timestamp", id) VALUES (300, 0, 300, '2024-12-07 08:00:00', '9d6b7c5e-a4f2-4a9b-b6c1-3e5f7d2b8a4f');
INSERT INTO prices (base_price, discount, final_price, "timestamp", id) VALUES (300, 10, 270, '2024-12-07 08:00:00', 'e1546489-1191-4d16-8f1b-1a834c916e49');
INSERT INTO prices (base_price, discount, final_price, "timestamp", id) VALUES (300, 10, 270, '2024-12-07 08:00:00', 'ca84aabb-51d4-43f7-bd6b-16b1cebda6dd');
INSERT INTO prices (base_price, discount, final_price, "timestamp", id) VALUES (300, 10, 270, '2024-12-07 08:00:00', '60b650c0-1372-4e9c-ac71-6fecc14fb3f7');
INSERT INTO prices (base_price, discount, final_price, "timestamp", id) VALUES (300, 10, 270, '2024-12-07 08:00:00', 'bd805644-991c-4207-8157-bc4ff8d2f1c3');
INSERT INTO prices (base_price, discount, final_price, "timestamp", id) VALUES (300, 10, 270, '2024-12-07 08:00:00', '3cb148b3-bc1b-472c-a985-43d5991592b1');


--
-- Data for Name: product_event_types; Type: TABLE DATA; Schema: public; Owner: -
--

INSERT INTO product_event_types (event_type_id, product_id) VALUES ('5de088d0-7bff-44f5-b144-51d6a24e740b', '11fbaacb-5d2e-44b0-8f7e-1d302baef461');
INSERT INTO product_event_types (event_type_id, product_id) VALUES ('5de088d0-7bff-44f5-b144-51d6a24e740b', 'df9a2350-c532-4a75-9cbf-5d5ea6fc807d');
INSERT INTO product_event_types (event_type_id, product_id) VALUES ('f0dcab26-9656-491e-bcce-bf23f723e998', '1237e35c-80ff-4a2a-8245-2728cb45ee11');
INSERT INTO product_event_types (event_type_id, product_id) VALUES ('4e7ec0af-af4e-463b-abe9-d8c2ba317d0a', '45067d8d-9d86-4104-97cd-7054c48cbbc6');
INSERT INTO product_event_types (event_type_id, product_id) VALUES ('5de088d0-7bff-44f5-b144-51d6a24e740b', '45067d8d-9d86-4104-97cd-7054c48cbbc6');
INSERT INTO product_event_types (event_type_id, product_id) VALUES ('4e7ec0af-af4e-463b-abe9-d8c2ba317d0a', '894d865c-0343-4a4a-9594-6ffc4f3a5a16');


--
-- Data for Name: product_pictures; Type: TABLE DATA; Schema: public; Owner: -
--

INSERT INTO product_pictures (product_id, picture_url) VALUES ('935e1b52-6180-419a-bbe8-909db6cd6cbc', '22.jpg');
INSERT INTO product_pictures (product_id, picture_url) VALUES ('11fbaacb-5d2e-44b0-8f7e-1d302baef461', '23.jpg');
INSERT INTO product_pictures (product_id, picture_url) VALUES ('df9a2350-c532-4a75-9cbf-5d5ea6fc807d', '24.jpg');
INSERT INTO product_pictures (product_id, picture_url) VALUES ('1237e35c-80ff-4a2a-8245-2728cb45ee11', '25.jpg');
INSERT INTO product_pictures (product_id, picture_url) VALUES ('45067d8d-9d86-4104-97cd-7054c48cbbc6', '26.jpg');
INSERT INTO product_pictures (product_id, picture_url) VALUES ('894d865c-0343-4a4a-9594-6ffc4f3a5a16', '27.jpg');
INSERT INTO product_pictures (product_id, picture_url) VALUES ('314c1838-8cbe-471c-9403-dc49baad1977', '28.jpg');
INSERT INTO product_pictures (product_id, picture_url) VALUES ('2eed4933-2477-487e-8b99-c39a9ac939dd', '16.jpg');
INSERT INTO product_pictures (product_id, picture_url) VALUES ('2eed4933-2477-487e-8b99-c39a9ac939dd', '29.jpg');


--
-- Data for Name: product_prices; Type: TABLE DATA; Schema: public; Owner: -
--

INSERT INTO product_prices (price_id, product_id) VALUES ('5d2f4a3b-c7a9-4e1a-b8f6-3e7a5c9d2b4f', '935e1b52-6180-419a-bbe8-909db6cd6cbc');
INSERT INTO product_prices (price_id, product_id) VALUES ('c7e5a3b9-d2f4-49b8-b1c9-5f7a3d9e6b4c', '11fbaacb-5d2e-44b0-8f7e-1d302baef461');
INSERT INTO product_prices (price_id, product_id) VALUES ('e1546489-1191-4d16-8f1b-1a834c916e49', '2eed4933-2477-487e-8b99-c39a9ac939dd');
INSERT INTO product_prices (price_id, product_id) VALUES ('9d6b7c5e-a4f2-4a9b-b6c1-3e5f7d2b8a4f', '314c1838-8cbe-471c-9403-dc49baad1977');
INSERT INTO product_prices (price_id, product_id) VALUES ('ca84aabb-51d4-43f7-bd6b-16b1cebda6dd', '894d865c-0343-4a4a-9594-6ffc4f3a5a16');
INSERT INTO product_prices (price_id, product_id) VALUES ('60b650c0-1372-4e9c-ac71-6fecc14fb3f7', '45067d8d-9d86-4104-97cd-7054c48cbbc6');
INSERT INTO product_prices (price_id, product_id) VALUES ('bd805644-991c-4207-8157-bc4ff8d2f1c3', '1237e35c-80ff-4a2a-8245-2728cb45ee11');
INSERT INTO product_prices (price_id, product_id) VALUES ('3cb148b3-bc1b-472c-a985-43d5991592b1', 'df9a2350-c532-4a75-9cbf-5d5ea6fc807d');


--
-- Data for Name: ratings; Type: TABLE DATA; Schema: public; Owner: -
--

INSERT INTO ratings (value, event_organizer_id, id, product_id) VALUES (4, '3f2b7e9a-6d4c-4b8f-b2a1-5c7e3d9f6b2a', 'a1b3d5f7-9d7c-4d2a-b6d7-e8a3f2d9b9f5', '935e1b52-6180-419a-bbe8-909db6cd6cbc');
INSERT INTO ratings (value, event_organizer_id, id, product_id) VALUES (5, '243d38ad-8ba3-48e0-99bd-0f2e31a710be', 'f4d5b6e9-8d3b-47c6-b8a7-d9c3e8b5d9b6', '11fbaacb-5d2e-44b0-8f7e-1d302baef461');
INSERT INTO ratings (value, event_organizer_id, id, product_id) VALUES (5, '243d38ad-8ba3-48e0-99bd-0f2e31a710be', 'a4d5b6e9-8d3b-47c6-b8a7-d9c3e8b5d9b6', '894d865c-0343-4a4a-9594-6ffc4f3a5a16');
INSERT INTO ratings (value, event_organizer_id, id, product_id) VALUES (4, '243d38ad-8ba3-48e0-99bd-0f2e31a710be', 'b4d5b6e9-8d3b-47c6-b8a7-d9c3e8b5d9b6', '314c1838-8cbe-471c-9403-dc49baad1977');
INSERT INTO ratings (value, event_organizer_id, id, product_id) VALUES (3, '243d38ad-8ba3-48e0-99bd-0f2e31a710be', 'c4d5b6e9-8d3b-47c6-b8a7-d9c3e8b5d9b6', '45067d8d-9d86-4104-97cd-7054c48cbbc6');
INSERT INTO ratings (value, event_organizer_id, id, product_id) VALUES (2, '243d38ad-8ba3-48e0-99bd-0f2e31a710be', 'd4d5b6e9-8d3b-47c6-b8a7-d9c3e8b5d9b6', '1237e35c-80ff-4a2a-8245-2728cb45ee11');
INSERT INTO ratings (value, event_organizer_id, id, product_id) VALUES (1, '243d38ad-8ba3-48e0-99bd-0f2e31a710be', 'e4d5b6e9-8d3b-47c6-b8a7-d9c3e8b5d9b6', 'df9a2350-c532-4a75-9cbf-5d5ea6fc807d');
INSERT INTO ratings (value, event_organizer_id, id, product_id) VALUES (3, '57c05a8a-dd8b-4da8-af73-90961e423f42', 'a7f9c5d6-b9e1-4a7a-b8e6-f9b2a8d5c6b4', 'df9a2350-c532-4a75-9cbf-5d5ea6fc807d');


--
-- Data for Name: reports; Type: TABLE DATA; Schema: public; Owner: -
--

INSERT INTO reports ("timestamp", id, reported_id, reporter_id, reason) VALUES ('2024-12-07 10:00:00', 'cd38da05-3026-4485-bc4d-82a527a2f93f', '97d6429d-d755-4337-a2b0-31f54633c538', '6a1c3423-e400-443f-93c1-9491b9dafb03', 'Inappropriate behavior');
INSERT INTO reports ("timestamp", id, reported_id, reporter_id, reason) VALUES ('2024-12-07 10:15:00', '80acd37d-2e79-405e-b349-78ff9895649d', '97d6429d-d755-4337-a2b0-31f54633c538', 'd7e7937b-02f3-47d7-bf64-f6e1555ff828', 'Spam content');
INSERT INTO reports ("timestamp", id, reported_id, reporter_id, reason) VALUES ('2024-12-07 10:30:00', 'fdcedddb-8b2c-4048-9d6d-9e7d7da2d758', '6a1c3423-e400-443f-93c1-9491b9dafb03', '97d6429d-d755-4337-a2b0-31f54633c538', 'Harassment');


--
-- Data for Name: reservations; Type: TABLE DATA; Schema: public; Owner: -
--

INSERT INTO reservations (end_time, start_time, "timestamp", event_id, id, product_id) VALUES ('2024-12-07 15:30:00', '2024-12-07 14:30:00', '2024-12-07 09:00:00', '3f7b2c9e-4a6f-4d5b-b8c1-7a2f9e3b6d4a', 'b9a7f3d9-c5e4-4a2a-b1e8-a9f7b2c9d8f4', '935e1b52-6180-419a-bbe8-909db6cd6cbc');
INSERT INTO reservations (end_time, start_time, "timestamp", event_id, id, product_id) VALUES ('2024-12-07 18:30:00', '2024-12-07 17:30:00', '2024-12-07 10:00:00', '4b3a7e9c-d8f5-49a1-b2c7-5a9d7f6e3c2b', 'a8e7d6f9-b5f8-42d2-9a7b-5d9f7c8d9b6a', '2eed4933-2477-487e-8b99-c39a9ac939dd');
INSERT INTO reservations (end_time, start_time, "timestamp", event_id, id, product_id) VALUES ('2024-12-07 18:30:00', '2024-12-07 17:30:00', '2024-12-07 10:00:00', '2d4a7c9e-6f3b-42a1-b8f5-3c7e9b6a4d5f', 'a8e7d6f9-b5f8-42d2-9a7b-5d9f7c8d9b6b', '2eed4933-2477-487e-8b99-c39a9ac939dd');


--
-- Data for Name: verification_tokens; Type: TABLE DATA; Schema: public; Owner: -
--



--
-- PostgreSQL database dump complete
--

