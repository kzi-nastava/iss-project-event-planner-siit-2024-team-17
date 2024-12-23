INSERT INTO locations (latitude, longitude, id, address, city)
VALUES
    (40.7128, -74.0060, 'a7c5e2b9-d3f4-49b8-b6c1-3f9e7a4d5b2c', '123 Main St', 'New York'),
    (34.0522, -118.2437, 'b6f3e9d2-c7a5-4b1a-b2c9-8d5e7c6f3a2b', '456 Elm St', 'Los Angeles'),
    (51.5074, -0.1278, '4e2d5b9f-a6c3-49a1-b8f5-7d9c7b6e3a2c', '789 Oak St', 'London');


INSERT INTO registration_requests (status, "timestamp", id)
VALUES
    (1, '2024-12-07 09:30:00', '5e6d739a-b529-44fb-98db-9cf95b7fe750'),
    (1, '2024-12-07 09:45:00', '2b221736-8f5b-4c2a-ab96-2a77d7d6274e'),
    (1, '2024-12-07 10:00:00', '537a4529-8241-4ac4-8d1b-94bb5b50355b'),

    (1, '2024-12-07 09:30:00', 'dbb94ace-5710-489c-85b3-4fcef244d9b9'),
    (1, '2024-12-07 09:45:00', 'b7627b61-ffd5-4664-9137-1f7421cbb12f'),
    (1, '2024-12-07 10:00:00', '53a359d9-f904-4cce-b4f4-cdf32c20552c'),

    (1, '2024-12-07 09:30:00', 'f18fe9b0-a620-45f9-b3b4-59c9d6929882'),
    (1, '2024-12-07 09:45:00', '4b7bcaa9-4c40-46d5-a1ba-a22d4e3f9778'),
    (1, '2024-12-07 10:00:00', 'd123fc95-e98a-473d-bd83-ac3a63a043b4'),


    (1, '2024-12-07 10:00:00', '48428b06-0175-4830-80fd-595d96f66906');


INSERT INTO persons (type, id, location_id, name, phone_number, profile_picture, surname)
VALUES
    (3, 'd7b9e5c3-a6f4-49a2-b8c1-7e3f9a2d6b4f', 'a7c5e2b9-d3f4-49b8-b6c1-3f9e7a4d5b2c', 'John', '1234567890', '1.png', 'Doe'),
    (3, '4b9c7f5a-d3e2-42a1-b6c8-3f7e9d5a2c6f', 'b6f3e9d2-c7a5-4b1a-b2c9-8d5e7c6f3a2b', 'Alice', '9876543210', '2.png', 'Smith'),
    (3, 'b7c9e2d4-a5b3-49c8-b2f7-d4a1c7e6f2d3', '4e2d5b9f-a6c3-49a1-b8f5-7d9c7b6e3a2c', 'Emma', '1122334455', '3.png', 'Brown'),
    (0, '20343059-c4dd-4608-81aa-104f415e8085', '4e2d5b9f-a6c3-49a1-b8f5-7d9c7b6e3a2c', 'Admin', '1122334455', '4.png', 'Admin');


INSERT INTO accounts (is_active, is_verified, type, suspension_timestamp, id, person_id, registration_request_id, email, password)
VALUES
    (TRUE, FALSE, 3, '2024-12-01 10:00:00', '97d6429d-d755-4337-a2b0-31f54633c538', 'd7b9e5c3-a6f4-49a2-b8c1-7e3f9a2d6b4f', '5e6d739a-b529-44fb-98db-9cf95b7fe750', 'user1@example.com', '$2a$10$Ly.0pBtncTCtqhCnklMFBerUhDO6La0f0ACuwJ/O6i8rowhDOAF36'),
    (FALSE, TRUE, 3, NULL, 'd7e7937b-02f3-47d7-bf64-f6e1555ff828', 'b7c9e2d4-a5b3-49c8-b2f7-d4a1c7e6f2d3', '2b221736-8f5b-4c2a-ab96-2a77d7d6274e', 'user2@example.com', '$2a$10$Ly.0pBtncTCtqhCnklMFBerUhDO6La0f0ACuwJ/O6i8rowhDOAF36'),
    (TRUE, TRUE, 3, '2024-12-07 14:45:00', '6a1c3423-e400-443f-93c1-9491b9dafb03', '4b9c7f5a-d3e2-42a1-b6c8-3f7e9d5a2c6f', '537a4529-8241-4ac4-8d1b-94bb5b50355b', 'user3@example.com', '$2a$10$Ly.0pBtncTCtqhCnklMFBerUhDO6La0f0ACuwJ/O6i8rowhDOAF36'),

    (TRUE, FALSE, 1, '2024-12-01 10:00:00', 'abd3ba1c-76a1-44c5-8204-6cbbcfbfda5d', 'a7c9e5b3-d4f2-49a1-b8c7-3e7f9a5b2c6d', 'dbb94ace-5710-489c-85b3-4fcef244d9b9', 'pup1@example.com', '$2a$10$Ly.0pBtncTCtqhCnklMFBerUhDO6La0f0ACuwJ/O6i8rowhDOAF36'),
    (FALSE, TRUE, 1, NULL, '1b02e7df-ea2a-4cb2-b93b-a067a7f12fc6', 'c7a9e5d3-f2b4-4a1b-b8c6-3f9e7a5b2d4f', 'b7627b61-ffd5-4664-9137-1f7421cbb12f', 'pup2@example.com', '$2a$10$Ly.0pBtncTCtqhCnklMFBerUhDO6La0f0ACuwJ/O6i8rowhDOAF36'),
    (TRUE, TRUE, 1, '2024-12-07 14:45:00', '31548d6b-019f-492e-ba39-07be7a1433e5', 'd7b5e9c3-a2f4-49b8-b6c1-3f9a7e5b2c8d', '53a359d9-f904-4cce-b4f4-cdf32c20552c', 'pup3@example.com', '$2a$10$Ly.0pBtncTCtqhCnklMFBerUhDO6La0f0ACuwJ/O6i8rowhDOAF36'),

    (TRUE, FALSE, 2, '2024-12-01 10:00:00', 'ae7adea0-516c-47b5-ae7b-10abcb32752d', '3f2b7e9a-6d4c-4b8f-b2a1-5c7e3d9f6b2a', 'f18fe9b0-a620-45f9-b3b4-59c9d6929882', 'organizer1@example.com', '$2a$10$Ly.0pBtncTCtqhCnklMFBerUhDO6La0f0ACuwJ/O6i8rowhDOAF36'),
    (FALSE, TRUE, 2, NULL, '49a1dae3-323c-460b-bbcd-0fc1132e6bb1', '243d38ad-8ba3-48e0-99bd-0f2e31a710be', '4b7bcaa9-4c40-46d5-a1ba-a22d4e3f9778', 'organzier2@example.com', '$2a$10$Ly.0pBtncTCtqhCnklMFBerUhDO6La0f0ACuwJ/O6i8rowhDOAF36'),
    (TRUE, TRUE, 2, '2024-12-07 14:45:00', '035ad44b-2ca3-4775-be2d-c65e6c1eb084', '57c05a8a-dd8b-4da8-af73-90961e423f42', 'd123fc95-e98a-473d-bd83-ac3a63a043b4', 'organizer3@example.com', '$2a$10$Ly.0pBtncTCtqhCnklMFBerUhDO6La0f0ACuwJ/O6i8rowhDOAF36'),

    (TRUE, TRUE, 0, '2024-12-01 10:00:00', 'e527b2da-66ae-4076-8301-d3a9dc3c5860', '20343059-c4dd-4608-81aa-104f415e8085', '48428b06-0175-4830-80fd-595d96f66906', 'admin@example.com', '$2a$10$Ly.0pBtncTCtqhCnklMFBerUhDO6La0f0ACuwJ/O6i8rowhDOAF36')
;


INSERT INTO event_types (is_deactivated, id, description, name)
VALUES
    (FALSE, 'f0dcab26-9656-491e-bcce-bf23f723e998', 'Outdoor events', 'Outdoor'),
    (TRUE, '5de088d0-7bff-44f5-b144-51d6a24e740b', 'Virtual events', 'Virtual'),
    (FALSE, '4e7ec0af-af4e-463b-abe9-d8c2ba317d0a', 'Corporate events', 'Corporate');


INSERT INTO event_organizers (type, id, location_id, name, phone_number, profile_picture, surname)
VALUES
    (2, '3f2b7e9a-6d4c-4b8f-b2a1-5c7e3d9f6b2a', '4e2d5b9f-a6c3-49a1-b8f5-7d9c7b6e3a2c', 'John', '1234567890', '5.png', 'Doe'),
    (2, '243d38ad-8ba3-48e0-99bd-0f2e31a710be', 'b6f3e9d2-c7a5-4b1a-b2c9-8d5e7c6f3a2b', 'Alice', '9876543210', '6.png', 'Smith'),
    (2, '57c05a8a-dd8b-4da8-af73-90961e423f42', 'a7c5e2b9-d3f4-49b8-b6c1-3f9e7a4d5b2c', 'Emma', '1122334455', '7.png', 'Brown');


INSERT INTO service_providers (type, work_end, work_start, company_location_id, id, location_id, company_description, company_email, company_name, name, phone_number, profile_picture, surname, company_phone_number)
VALUES
    (1, '18:00:00', '09:00:00', '4e2d5b9f-a6c3-49a1-b8f5-7d9c7b6e3a2c', 'a7c9e5b3-d4f2-49a1-b8c7-3e7f9a5b2c6d', 'a7c5e2b9-d3f4-49b8-b6c1-3f9e7a4d5b2c', 'Tech solutions for small businesses', 'info@techcorp.com', 'TechCorp', 'John', '1234567890', '8.png', 'Doe', '1234567890'),
    (1, '17:00:00', '08:00:00', 'b6f3e9d2-c7a5-4b1a-b2c9-8d5e7c6f3a2b', 'c7a9e5d3-f2b4-4a1b-b8c6-3f9e7a5b2d4f', 'a7c5e2b9-d3f4-49b8-b6c1-3f9e7a4d5b2c', 'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Proin posuere pulvinar est, et molestie justo tempus in. Phasellus vitae mi ac libero posuere efficitur. Donec laoreet turpis lectus, eget lacinia leo eleifend gravida.', 'contact@bizconsult.com', 'BizConsult', 'Alice', '9876543210', '9.png', 'Smith', '1234567890'),
    (1, '20:00:00', '10:00:00', 'a7c5e2b9-d3f4-49b8-b6c1-3f9e7a4d5b2c', 'd7b5e9c3-a2f4-49b8-b6c1-3f9a7e5b2c8d', '4e2d5b9f-a6c3-49a1-b8f5-7d9c7b6e3a2c', 'Offering creative solutions for marketing. Something something something something ', 'support@creativehub.com', 'CreativeHub', 'Emma', '1122334455', '10.png', 'Brown', '1234567890');


INSERT INTO categories (is_deleted, status, id, description, name)
VALUES
    (FALSE, 0, 'a7c5e2b9-d3f4-49b8-b6c1-3f9e7a4d5b2c', 'Category for technology', 'Technology'),
    (FALSE, 1, 'b1d5d5c5-f6c1-4039-a8a5-4fc3ea0a4e2a', 'Category for technology', 'Technology'),
    (FALSE, 1, 'd4f4e6b7-d2d5-4376-8a9b-7c4f3b3c1e7d', 'Category for arts and culture', 'Arts'),
    (FALSE, 1, 'c7d2b4f3-8c4f-432e-8b5c-9a1d3f1b8d5a', 'Deprecated category', 'Legacy');

INSERT INTO reports ("timestamp", id, reported_id, reporter_id, reason)
VALUES
    ('2024-12-07 10:00:00', 'cd38da05-3026-4485-bc4d-82a527a2f93f', '97d6429d-d755-4337-a2b0-31f54633c538', '6a1c3423-e400-443f-93c1-9491b9dafb03', 'Inappropriate behavior'),
    ('2024-12-07 10:15:00', '80acd37d-2e79-405e-b349-78ff9895649d', '97d6429d-d755-4337-a2b0-31f54633c538', 'd7e7937b-02f3-47d7-bf64-f6e1555ff828', 'Spam content'),
    ('2024-12-07 10:30:00', 'fdcedddb-8b2c-4048-9d6d-9e7d7da2d758', '6a1c3423-e400-443f-93c1-9491b9dafb03', '97d6429d-d755-4337-a2b0-31f54633c538', 'Harassment');


INSERT INTO messages ("timestamp", from_id, id, to_id, content)
VALUES
    ('2024-12-07 09:00:00', '31548d6b-019f-492e-ba39-07be7a1433e5', 'b2c9e3a5-d4f7-4b8f-b7e1-3f9c6a2d5b7e', '97d6429d-d755-4337-a2b0-31f54633c538', 'Hello, how are you?'),
    ('2024-12-07 09:05:00', '49a1dae3-323c-460b-bbcd-0fc1132e6bb1', 'e5c7b3a9-d2f6-4a1a-b8c9-7f3e6b2d9a4f', '6a1c3423-e400-443f-93c1-9491b9dafb03', 'I am good, thank you!'),
    ('2024-12-07 09:10:00', '6a1c3423-e400-443f-93c1-9491b9dafb03', '7e5a3c9d-48f1-432a-b6c1-3e7a8f5b2d9e', '49a1dae3-323c-460b-bbcd-0fc1132e6bb1', 'See you at the event.');


INSERT INTO blocks ("timestamp", blocked_id, who_id, id)
VALUES
    ('2024-12-07 15:00:00', '6a1c3423-e400-443f-93c1-9491b9dafb03', '97d6429d-d755-4337-a2b0-31f54633c538', 'f4bb3025-88e7-4dc8-835e-6fda3279f8c8'),
    ('2024-12-07 16:00:00', 'd7e7937b-02f3-47d7-bf64-f6e1555ff828', '97d6429d-d755-4337-a2b0-31f54633c538', '5db5b1b6-3a15-40b5-a8ec-72e7e62a8fbf'),
    ('2024-12-07 17:00:00',  'd7e7937b-02f3-47d7-bf64-f6e1555ff828', '6a1c3423-e400-443f-93c1-9491b9dafb03', '9dbedc2e-4d54-4c4b-902b-49f3f1d6bda5');


INSERT INTO category_event_types (category_id, event_type_id)
VALUES
    ('c7d2b4f3-8c4f-432e-8b5c-9a1d3f1b8d5a', '4e7ec0af-af4e-463b-abe9-d8c2ba317d0a'),
    ('b1d5d5c5-f6c1-4039-a8a5-4fc3ea0a4e2a', '4e7ec0af-af4e-463b-abe9-d8c2ba317d0a'),
    ('d4f4e6b7-d2d5-4376-8a9b-7c4f3b3c1e7d', '5de088d0-7bff-44f5-b144-51d6a24e740b'),
    ('d4f4e6b7-d2d5-4376-8a9b-7c4f3b3c1e7d', 'f0dcab26-9656-491e-bcce-bf23f723e998');

INSERT INTO company_pictures (service_provider_id, picture_url)
VALUES
    ('a7c9e5b3-d4f2-49a1-b8c7-3e7f9a5b2c6d', '11.jpg'),
    ('c7a9e5d3-f2b4-4a1b-b8c6-3f9e7a5b2d4f', '12.jpg'),
    ('c7a9e5d3-f2b4-4a1b-b8c6-3f9e7a5b2d4f', '13.jpg'),
    ('d7b5e9c3-a2f4-49b8-b6c1-3f9a7e5b2c8d', '14.jpg');


INSERT INTO events (max_attendance, privacy, "time", event_type_id, event_organizer_id, id, location_id, description, name, picture)
VALUES
    (100, 0, '2024-12-10 10:00:00', 'f0dcab26-9656-491e-bcce-bf23f723e998', '57c05a8a-dd8b-4da8-af73-90961e423f42', '3f7b2c9e-4a6f-4d5b-b8c1-7a2f9e3b6d4a', '4e2d5b9f-a6c3-49a1-b8f5-7d9c7b6e3a2c', 'Annual gathering', 'Tech Meetup', '15.jpg'),
    (200, 1, '2024-12-20 18:00:00', '5de088d0-7bff-44f5-b144-51d6a24e740b', '57c05a8a-dd8b-4da8-af73-90961e423f42', '2d4a7c9e-6f3b-42a1-b8f5-3c7e9b6a4d5f', 'a7c5e2b9-d3f4-49b8-b6c1-3f9e7a4d5b2c', 'Holiday party', 'Winter Fest', '16.jpg'),
    (300, 1, '2024-12-15 18:00:00', '5de088d0-7bff-44f5-b144-51d6a24e740b', '243d38ad-8ba3-48e0-99bd-0f2e31a710be', '8c216a1f-6d65-4256-95e0-6a820d5fb902', 'a7c5e2b9-d3f4-49b8-b6c1-3f9e7a4d5b2c', 'Birthday party', 'Birthday', '17.jpg'),
    (100, 1, '2024-12-15 18:00:00', '5de088d0-7bff-44f5-b144-51d6a24e740b', '243d38ad-8ba3-48e0-99bd-0f2e31a710be', '06aee816-a4ec-4d3c-9d9c-2c0f6bb96285', 'a7c5e2b9-d3f4-49b8-b6c1-3f9e7a4d5b2c', 'Anniversary party', 'Anniversary', '18.jpg'),
    (50, 1, '2024-12-15 18:00:00', '5de088d0-7bff-44f5-b144-51d6a24e740b', '243d38ad-8ba3-48e0-99bd-0f2e31a710be', '6915ce46-d213-424b-a3c4-035767714df0', 'a7c5e2b9-d3f4-49b8-b6c1-3f9e7a4d5b2c', 'Halloween party', 'halloween', '19.jpg'),
    (400, 1, '2024-12-15 18:00:00', '5de088d0-7bff-44f5-b144-51d6a24e740b', '243d38ad-8ba3-48e0-99bd-0f2e31a710be', 'f1ad3604-fef5-439a-8adb-45776a019a55', 'a7c5e2b9-d3f4-49b8-b6c1-3f9e7a4d5b2c', 'Birthday party', ' Birthday', '20.jpg'),
    (800, 0, '2024-12-20 14:00:00', '4e7ec0af-af4e-463b-abe9-d8c2ba317d0a', '3f2b7e9a-6d4c-4b8f-b2a1-5c7e3d9f6b2a', '4b3a7e9c-d8f5-49a1-b2c7-5a9d7f6e3c2b', 'a7c5e2b9-d3f4-49b8-b6c1-3f9e7a4d5b2c', 'Local festival', 'Spring Fair', '21.jpg');


INSERT INTO persons_attending_events (event_id, person_id)
VALUES
    ('3f7b2c9e-4a6f-4d5b-b8c1-7a2f9e3b6d4a', 'd7b9e5c3-a6f4-49a2-b8c1-7e3f9a2d6b4f'),
    ('2d4a7c9e-6f3b-42a1-b8f5-3c7e9b6a4d5f', '4b9c7f5a-d3e2-42a1-b6c8-3f7e9d5a2c6f'),

    ('3f7b2c9e-4a6f-4d5b-b8c1-7a2f9e3b6d4a','d7b5e9c3-a2f4-49b8-b6c1-3f9a7e5b2c8d'),
    ('4b3a7e9c-d8f5-49a1-b2c7-5a9d7f6e3c2b','d7b5e9c3-a2f4-49b8-b6c1-3f9a7e5b2c8d'),
    ('8c216a1f-6d65-4256-95e0-6a820d5fb902','d7b5e9c3-a2f4-49b8-b6c1-3f9a7e5b2c8d'),


    ('3f7b2c9e-4a6f-4d5b-b8c1-7a2f9e3b6d4a','57c05a8a-dd8b-4da8-af73-90961e423f42'),
    ('4b3a7e9c-d8f5-49a1-b2c7-5a9d7f6e3c2b','57c05a8a-dd8b-4da8-af73-90961e423f42'),
    ('8c216a1f-6d65-4256-95e0-6a820d5fb902','57c05a8a-dd8b-4da8-af73-90961e423f42'),

    ('4b3a7e9c-d8f5-49a1-b2c7-5a9d7f6e3c2b', 'b7c9e2d4-a5b3-49c8-b2f7-d4a1c7e6f2d3');


INSERT INTO persons_favorite_events (event_id, person_id)
VALUES
    ('3f7b2c9e-4a6f-4d5b-b8c1-7a2f9e3b6d4a', 'd7b9e5c3-a6f4-49a2-b8c1-7e3f9a2d6b4f'),
    ('2d4a7c9e-6f3b-42a1-b8f5-3c7e9b6a4d5f', '4b9c7f5a-d3e2-42a1-b6c8-3f7e9d5a2c6f'),
    ('2d4a7c9e-6f3b-42a1-b8f5-3c7e9b6a4d5f','d7b5e9c3-a2f4-49b8-b6c1-3f9a7e5b2c8d'),
    ('3f7b2c9e-4a6f-4d5b-b8c1-7a2f9e3b6d4a','d7b5e9c3-a2f4-49b8-b6c1-3f9a7e5b2c8d'),
    ('4b3a7e9c-d8f5-49a1-b2c7-5a9d7f6e3c2b','d7b5e9c3-a2f4-49b8-b6c1-3f9a7e5b2c8d'),
    ('8c216a1f-6d65-4256-95e0-6a820d5fb902','d7b5e9c3-a2f4-49b8-b6c1-3f9a7e5b2c8d'),
    ('06aee816-a4ec-4d3c-9d9c-2c0f6bb96285','d7b5e9c3-a2f4-49b8-b6c1-3f9a7e5b2c8d'),
    ('6915ce46-d213-424b-a3c4-035767714df0','d7b5e9c3-a2f4-49b8-b6c1-3f9a7e5b2c8d'),
    ('f1ad3604-fef5-439a-8adb-45776a019a55','d7b5e9c3-a2f4-49b8-b6c1-3f9a7e5b2c8d'),
    ('4b3a7e9c-d8f5-49a1-b2c7-5a9d7f6e3c2b', 'b7c9e2d4-a5b3-49c8-b2f7-d4a1c7e6f2d3');


INSERT INTO invitations (status, "timestamp", event_id, id, picture, target_email)
VALUES
    (0, '2024-12-08 12:00:00', '3f7b2c9e-4a6f-4d5b-b8c1-7a2f9e3b6d4a', 'f5b2d3c9-7e6a-49a1-b8c5-3e7f9d2a4b6c', 'https://example.com/invitation1.jpg', 'vanjakostic03@gmail.com'),
    (1, '2024-12-09 15:30:00', '2d4a7c9e-6f3b-42a1-b8f5-3c7e9b6a4d5f', '7f2a3b9e-6d4c-4b8f-b2a1-5c7e3d9f6b2a', 'https://example.com/invitation2.jpg', 'attendee2@example.com'),
    (2, '2024-12-10 20:00:00', '4b3a7e9c-d8f5-49a1-b2c7-5a9d7f6e3c2b', '3c9a7b2e-4d5f-4a8b-b1c7-5f3e2d9b6a4c', 'https://example.com/invitation3.jpg', 'attendee3@example.com');


INSERT INTO prices (base_price, discount, final_price, "timestamp", id)
VALUES
    (100.00, 10.00, 90.00, '2024-12-07 07:30:00', '5d2f4a3b-c7a9-4e1a-b8f6-3e7a5c9d2b4f'),
    (200.00, 20.00, 160.00, '2024-12-07 07:45:00', 'c7e5a3b9-d2f4-49b8-b1c9-5f7a3d9e6b4c'),
    (300.00, 0.00, 300.00, '2024-12-07 08:00:00', '9d6b7c5e-a4f2-4a9b-b6c1-3e5f7d2b8a4f'),
    (300.00, 10.00, 270.00, '2024-12-07 08:00:00', 'e1546489-1191-4d16-8f1b-1a834c916e49'),
    (300.00, 10.00, 270.00, '2024-12-07 08:00:00', 'ca84aabb-51d4-43f7-bd6b-16b1cebda6dd'),
    (300.00, 10.00, 270.00, '2024-12-07 08:00:00', '60b650c0-1372-4e9c-ac71-6fecc14fb3f7'),
    (300.00, 10.00, 270.00, '2024-12-07 08:00:00', 'bd805644-991c-4207-8157-bc4ff8d2f1c3'),
    (300.00, 10.00, 270.00, '2024-12-07 08:00:00', '3cb148b3-bc1b-472c-a985-43d5991592b1');


INSERT INTO agenda_activities (end_time, start_time, event_id, id, description, location_name, name)
VALUES
    ('2024-12-07 15:30:00', '2024-12-07 14:30:00', '3f7b2c9e-4a6f-4d5b-b8c1-7a2f9e3b6d4a', 'b9a7f3d9-c5e4-4a2a-b1e8-a9f7b2c9d8f4', 'Keynote speech by the CEO', 'Main Hall', 'Opening Ceremony'),
    ('2024-12-07 17:00:00', '2024-12-07 16:00:00', '2d4a7c9e-6f3b-42a1-b8f5-3c7e9b6a4d5f', 'e9d2c5f7-3e7f-4a6d-b1f7-d9b5e7d2a8b9', 'Networking session with industry leaders', 'Conference Room 1', 'Networking Session'),
    ('2024-12-07 18:30:00', '2024-12-07 17:30:00', '4b3a7e9c-d8f5-49a1-b2c7-5a9d7f6e3c2b', 'a8e7d6f9-b5f8-42d2-9a7b-5d9f7c8d9b6a', 'Panel discussion on emerging trends in technology', 'Conference Room 2', 'Panel Discussion');


INSERT INTO products (cancellation_window_days, duration_minutes, is_auto_accept, is_available, is_deleted, is_visible, reservation_window_days, status, edit_timestamp, category_id, id, service_provider_id, type, description, name)
VALUES
    (5, 180, false, true, false, true, 7, 1, '2024-12-07 09:45:00', 'd4f4e6b7-d2d5-4376-8a9b-7c4f3b3c1e7d','11fbaacb-5d2e-44b0-8f7e-1d302baef461', 'c7a9e5d3-f2b4-4a1b-b8c6-3f9e7a5b2d4f', 'PRODUCT', 'A cleaning service for offices', 'Office Cleaning1'),
    (5, 180, false, true, false, true, 7, 1, '2024-12-07 09:45:00', 'd4f4e6b7-d2d5-4376-8a9b-7c4f3b3c1e7d','df9a2350-c532-4a75-9cbf-5d5ea6fc807d', 'c7a9e5d3-f2b4-4a1b-b8c6-3f9e7a5b2d4f', 'PRODUCT', 'A cleaning service for offices', 'Office Cleaning2'),
    (5, 180, false, true, false, true, 7, 1, '2024-12-07 09:45:00', 'd4f4e6b7-d2d5-4376-8a9b-7c4f3b3c1e7d','1237e35c-80ff-4a2a-8245-2728cb45ee11', 'c7a9e5d3-f2b4-4a1b-b8c6-3f9e7a5b2d4f', 'PRODUCT', 'A cleaning service for offices', 'Office Cleaning3'),
    (5, 180, false, true, false, true, 7, 1, '2024-12-07 09:45:00', 'd4f4e6b7-d2d5-4376-8a9b-7c4f3b3c1e7d','45067d8d-9d86-4104-97cd-7054c48cbbc6', 'c7a9e5d3-f2b4-4a1b-b8c6-3f9e7a5b2d4f', 'PRODUCT', 'A cleaning service for offices', 'Office Cleaning4'),
    (5, 180, false, false, false, true, 7, 0, '2024-12-07 09:45:00', 'a7c5e2b9-d3f4-49b8-b6c1-3f9e7a4d5b2c','894d865c-0343-4a4a-9594-6ffc4f3a5a16', 'c7a9e5d3-f2b4-4a1b-b8c6-3f9e7a5b2d4f', 'PRODUCT', 'A cleaning service for offices', 'Office Cleaning6'),
    (3, 120, true, true, false, true, 5, 1, '2024-12-07 09:30:00', 'b1d5d5c5-f6c1-4039-a8a5-4fc3ea0a4e2a','935e1b52-6180-419a-bbe8-909db6cd6cbc', 'a7c9e5b3-d4f2-49a1-b8c7-3e7f9a5b2c6d', 'SERVICE', 'A professional IT consultation', 'Tech Consultation'),
    (5, 180, false, true, false, true, 7, 1, '2024-12-07 09:45:00', 'd4f4e6b7-d2d5-4376-8a9b-7c4f3b3c1e7d','314c1838-8cbe-471c-9403-dc49baad1977', 'c7a9e5d3-f2b4-4a1b-b8c6-3f9e7a5b2d4f', 'SERVICE', 'A cleaning service for offices', 'Office Cleaning5'),
    (10, 90, true, false, false, true, 3, 1, '2024-12-07 10:00:00', 'd4f4e6b7-d2d5-4376-8a9b-7c4f3b3c1e7d','2eed4933-2477-487e-8b99-c39a9ac939dd', 'c7a9e5d3-f2b4-4a1b-b8c6-3f9e7a5b2d4f', 'SERVICE', 'A professional cleaning service for homes', 'Home Cleaning');

INSERT INTO reservations (end_time, start_time, "timestamp", event_id, id, product_id)
VALUES
    ('2024-12-07 15:30:00', '2024-12-07 14:30:00', '2024-12-07 09:00:00', '3f7b2c9e-4a6f-4d5b-b8c1-7a2f9e3b6d4a', 'b9a7f3d9-c5e4-4a2a-b1e8-a9f7b2c9d8f4', '935e1b52-6180-419a-bbe8-909db6cd6cbc'),
    ('2024-12-07 17:00:00', '2024-12-07 16:00:00', '2024-12-07 09:30:00', '2d4a7c9e-6f3b-42a1-b8f5-3c7e9b6a4d5f', 'e9d2c5f7-3e7f-4a6d-b1f7-d9b5e7d2a8b9', '11fbaacb-5d2e-44b0-8f7e-1d302baef461'),
    ('2024-12-07 18:30:00', '2024-12-07 17:30:00', '2024-12-07 10:00:00', '4b3a7e9c-d8f5-49a1-b2c7-5a9d7f6e3c2b', 'a8e7d6f9-b5f8-42d2-9a7b-5d9f7c8d9b6a', '2eed4933-2477-487e-8b99-c39a9ac939dd');


INSERT INTO ratings (value, event_organizer_id, id, product_id)
VALUES
    (4, '3f2b7e9a-6d4c-4b8f-b2a1-5c7e3d9f6b2a', 'a1b3d5f7-9d7c-4d2a-b6d7-e8a3f2d9b9f5', '935e1b52-6180-419a-bbe8-909db6cd6cbc'),
    (5, '243d38ad-8ba3-48e0-99bd-0f2e31a710be', 'f4d5b6e9-8d3b-47c6-b8a7-d9c3e8b5d9b6', '11fbaacb-5d2e-44b0-8f7e-1d302baef461'),
    (5, '243d38ad-8ba3-48e0-99bd-0f2e31a710be', 'a4d5b6e9-8d3b-47c6-b8a7-d9c3e8b5d9b6', '894d865c-0343-4a4a-9594-6ffc4f3a5a16'),
    (4, '243d38ad-8ba3-48e0-99bd-0f2e31a710be', 'b4d5b6e9-8d3b-47c6-b8a7-d9c3e8b5d9b6', '314c1838-8cbe-471c-9403-dc49baad1977'),
    (3, '243d38ad-8ba3-48e0-99bd-0f2e31a710be', 'c4d5b6e9-8d3b-47c6-b8a7-d9c3e8b5d9b6', '45067d8d-9d86-4104-97cd-7054c48cbbc6'),
    (2, '243d38ad-8ba3-48e0-99bd-0f2e31a710be', 'd4d5b6e9-8d3b-47c6-b8a7-d9c3e8b5d9b6', '1237e35c-80ff-4a2a-8245-2728cb45ee11'),
    (1, '243d38ad-8ba3-48e0-99bd-0f2e31a710be', 'e4d5b6e9-8d3b-47c6-b8a7-d9c3e8b5d9b6', 'df9a2350-c532-4a75-9cbf-5d5ea6fc807d'),
    (3, '57c05a8a-dd8b-4da8-af73-90961e423f42', 'a7f9c5d6-b9e1-4a7a-b8e6-f9b2a8d5c6b4', '2eed4933-2477-487e-8b99-c39a9ac939dd');


INSERT INTO product_prices (price_id, product_id)
VALUES
    ('5d2f4a3b-c7a9-4e1a-b8f6-3e7a5c9d2b4f', '935e1b52-6180-419a-bbe8-909db6cd6cbc'),
    ('c7e5a3b9-d2f4-49b8-b1c9-5f7a3d9e6b4c', '11fbaacb-5d2e-44b0-8f7e-1d302baef461'),
    ('e1546489-1191-4d16-8f1b-1a834c916e49', '2eed4933-2477-487e-8b99-c39a9ac939dd'),
    ('9d6b7c5e-a4f2-4a9b-b6c1-3e5f7d2b8a4f', '314c1838-8cbe-471c-9403-dc49baad1977'),
    ('ca84aabb-51d4-43f7-bd6b-16b1cebda6dd', '894d865c-0343-4a4a-9594-6ffc4f3a5a16'),
    ('60b650c0-1372-4e9c-ac71-6fecc14fb3f7', '45067d8d-9d86-4104-97cd-7054c48cbbc6'),
    ('bd805644-991c-4207-8157-bc4ff8d2f1c3', '1237e35c-80ff-4a2a-8245-2728cb45ee11'),
    ('3cb148b3-bc1b-472c-a985-43d5991592b1', 'df9a2350-c532-4a75-9cbf-5d5ea6fc807d');


INSERT INTO product_event_types (event_type_id, product_id)
VALUES
    ('4e7ec0af-af4e-463b-abe9-d8c2ba317d0a', '935e1b52-6180-419a-bbe8-909db6cd6cbc'),
    ('5de088d0-7bff-44f5-b144-51d6a24e740b', '314c1838-8cbe-471c-9403-dc49baad1977'),
    ('f0dcab26-9656-491e-bcce-bf23f723e998', '314c1838-8cbe-471c-9403-dc49baad1977'),
    ('5de088d0-7bff-44f5-b144-51d6a24e740b', '11fbaacb-5d2e-44b0-8f7e-1d302baef461'),
    ('f0dcab26-9656-491e-bcce-bf23f723e998', '2eed4933-2477-487e-8b99-c39a9ac939dd');


INSERT INTO product_pictures (product_id, picture_url)
VALUES
    ('935e1b52-6180-419a-bbe8-909db6cd6cbc', '22.jpg'),
    ('11fbaacb-5d2e-44b0-8f7e-1d302baef461', '23.jpg'),
    ('df9a2350-c532-4a75-9cbf-5d5ea6fc807d', '24.jpg'),
    ('1237e35c-80ff-4a2a-8245-2728cb45ee11', '25.jpg'),
    ('45067d8d-9d86-4104-97cd-7054c48cbbc6', '26.jpg'),
    ('894d865c-0343-4a4a-9594-6ffc4f3a5a16', '27.jpg'),
    ('314c1838-8cbe-471c-9403-dc49baad1977', '28.jpg'),
    ('2eed4933-2477-487e-8b99-c39a9ac939dd', '16.jpg'),
    ('2eed4933-2477-487e-8b99-c39a9ac939dd', '29.jpg');

INSERT INTO persons_favorite_products (person_id, product_id)
VALUES
    ('d7b9e5c3-a6f4-49a2-b8c1-7e3f9a2d6b4f', '2eed4933-2477-487e-8b99-c39a9ac939dd'),
    ('4b9c7f5a-d3e2-42a1-b6c8-3f7e9d5a2c6f', '11fbaacb-5d2e-44b0-8f7e-1d302baef461'),

    ('d7b5e9c3-a2f4-49b8-b6c1-3f9a7e5b2c8d', '2eed4933-2477-487e-8b99-c39a9ac939dd'),
    ('d7b5e9c3-a2f4-49b8-b6c1-3f9a7e5b2c8d', '11fbaacb-5d2e-44b0-8f7e-1d302baef461'),
    ('d7b5e9c3-a2f4-49b8-b6c1-3f9a7e5b2c8d', '1237e35c-80ff-4a2a-8245-2728cb45ee11'),
    ('d7b5e9c3-a2f4-49b8-b6c1-3f9a7e5b2c8d', '314c1838-8cbe-471c-9403-dc49baad1977'),
    ('d7b5e9c3-a2f4-49b8-b6c1-3f9a7e5b2c8d', '935e1b52-6180-419a-bbe8-909db6cd6cbc'),
    ('d7b5e9c3-a2f4-49b8-b6c1-3f9a7e5b2c8d', 'df9a2350-c532-4a75-9cbf-5d5ea6fc807d'),


    ('b7c9e2d4-a5b3-49c8-b2f7-d4a1c7e6f2d3', '935e1b52-6180-419a-bbe8-909db6cd6cbc');


INSERT INTO comments (status, author_id, id, product_id, content)
VALUES
    (1, '3f2b7e9a-6d4c-4b8f-b2a1-5c7e3d9f6b2a', 'e52d2a61-abc7-42e9-82d4-7b3f52a4a1c5', '2eed4933-2477-487e-8b99-c39a9ac939dd', 'Great product!'),
    (1, '3f2b7e9a-6d4c-4b8f-b2a1-5c7e3d9f6b2a', 'f92c4b63-d2a1-49d7-8f1e-9b4c72e1a5f3', '2eed4933-2477-487e-8b99-c39a9ac939dd', 'Needs improvement.'),
    (1, '57c05a8a-dd8b-4da8-af73-90961e423f42', '3b9e6a2c-d7f2-4a3b-b2c9-7e5f4b6a1c8d', '935e1b52-6180-419a-bbe8-909db6cd6cbc', 'Highly recommended!');


INSERT INTO notifications ("timestamp", event_id, id, product_id, content)
VALUES
    ('2024-12-07 08:00:00', '3f7b2c9e-4a6f-4d5b-b8c1-7a2f9e3b6d4a', 'c7a5f4b9-d2e3-4a1a-b6f5-3c9e8d7b2a4c', NULL, 'Event reminder: Tech Meetup'),
    ('2024-12-07 08:15:00', NULL, '7c5f3e9a-d8b1-4a2c-b9f6-5a3e7d2b8c4f', '2eed4933-2477-487e-8b99-c39a9ac939dd', 'New product available: Widget X'),
    ('2024-12-07 08:30:00', '4b3a7e9c-d8f5-49a1-b2c7-5a9d7f6e3c2b', '3c7f9e5b-6a2d-42a9-b8c5-7d2f3b9e6a4f', NULL, 'Event cancelled: Spring Fair');


INSERT INTO persons_notifications (notification_id, person_id)
VALUES
    ('c7a5f4b9-d2e3-4a1a-b6f5-3c9e8d7b2a4c', 'd7b9e5c3-a6f4-49a2-b8c1-7e3f9a2d6b4f'),
    ('7c5f3e9a-d8b1-4a2c-b9f6-5a3e7d2b8c4f', '4b9c7f5a-d3e2-42a1-b6c8-3f7e9d5a2c6f'),
    ('3c7f9e5b-6a2d-42a9-b8c5-7d2f3b9e6a4f', 'b7c9e2d4-a5b3-49c8-b2f7-d4a1c7e6f2d3');
