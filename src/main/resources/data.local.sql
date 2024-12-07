INSERT INTO locations (latitude, longitude, id, address, city)
VALUES
    (40.7128, -74.0060, 'a7c5e2b9-d3f4-49b8-b6c1-3f9e7a4d5b2c', '123 Main St', 'New York'),
    (34.0522, -118.2437, 'b6f3e9d2-c7a5-4b1a-b2c9-8d5e7c6f3a2b', '456 Elm St', 'Los Angeles'),
    (51.5074, -0.1278, '4e2d5b9f-a6c3-49a1-b8f5-7d9c7b6e3a2c', '789 Oak St', 'London');


INSERT INTO registration_requests (status, "timestamp", id)
VALUES
    (1, '2024-12-07 09:30:00', '7f3d8e7b-9d3b-4a7c-b9e6-d2f7a7d6b7e5'),
    (0, '2024-12-07 09:45:00', '6f9c8e7b-4d6f-49a9-b1d9-f3e8b5d7c9d4'),
    (2, '2024-12-07 10:00:00', '4e9b5d6a-8d3a-4a7f-8b5e-d9f7b9e6a8f2');


INSERT INTO persons (type, id, location_id, name, phone_number, profile_picture, surname)
VALUES
    (0, 'd7b9e5c3-a6f4-49a2-b8c1-7e3f9a2d6b4f', '7e5f4c2a-d8b9-4b3a-b1f6-3c7a8f5b2d9e', 'John', '+1234567890', 'https://example.com/john.jpg', 'Doe'),
    (1, '4b9c7f5a-d3e2-42a1-b6c8-3f7e9d5a2c6f', '9f8e7c3a-4b6f-4d2a-b5e1-7a3f9c6e2b4d', 'Alice', '+9876543210', 'https://example.com/alice.jpg', 'Smith'),
    (2, 'b7c9e2d4-a5b3-49c8-b2f7-d4a1c7e6f2d3', '7a2d5b9e-4c7f-49a1-b8f6-5c9e3f2a6b4d', 'Emma', '+1122334455', 'https://example.com/emma.jpg', 'Brown');


INSERT INTO accounts (is_active, is_verified, type, suspension_timestamp, id, person_id, registration_request_id, email, password)
VALUES
    (TRUE, FALSE, 1, '2024-12-01 10:00:00', 'b91d1c84-6b4f-4e58-9463-332d40e71a80', 'd2a4b233-6fc1-47fa-9b67-8d60c1c7c612', '839038b4-982e-4e7b-823e-9dca8dc2fa0f', 'user1@example.com', 'password123'),
    (FALSE, TRUE, 2, NULL, 'eb1f1a55-9f26-4b42-8a80-5ff34f77c7ec', 'bb3fbb15-09a6-4327-9205-75e56e2e33f7', NULL, 'user2@example.com', 'password456'),
    (TRUE, TRUE, 3, '2024-12-07 14:45:00', '4c4b8357-2dd9-4a61-a9e6-1e86a93bb78e', 'd3a19b1e-cd58-4cda-96b5-8f728f4d1b7a', '5415d9c3-5643-490e-9f9e-94f91f88b8e6', 'user3@example.com', 'password789');


INSERT INTO event_types (is_deactivated, id, description, name)
VALUES
    (FALSE, 'a7c5e2b9-d3f4-49b8-b6c1-3f9e7a4d5b2c', 'Outdoor events', 'Outdoor'),
    (TRUE, 'b6f3e9d2-c7a5-4b1a-b2c9-8d5e7c6f3a2b', 'Virtual events', 'Virtual'),
    (FALSE, '4e2d5b9f-a6c3-49a1-b8f5-7d9c7b6e3a2c', 'Corporate events', 'Corporate');


INSERT INTO event_organizers (type, id, location_id, name, phone_number, profile_picture, surname)
VALUES
    (0, 'b7c9e2d4-a5b3-49c8-b2f7-d4a1c7e6f2d3', '7e5f4c2a-d8b9-4b3a-b1f6-3c7a8f5b2d9e', 'John', '+1234567890', 'https://example.com/john.jpg', 'Doe'),
    (1, '3f2b7e9a-6d4c-4b8f-b2a1-5c7e3d9f6b2a', '5d7e4b6a-c8f9-4a2b-b3e1-9f6c8d2a5b7e', 'Alice', '+9876543210', 'https://example.com/alice.jpg', 'Smith'),
    (2, '4b9c7f5a-d3e2-42a1-b6c8-3f7e9d5a2c6f', '9f8e7c3a-4b6f-4d2a-b5e1-7a3f9c6e2b4d', 'Emma', '+1122334455', 'https://example.com/emma.jpg', 'Brown');


INSERT INTO service_providers (type, work_end, work_start, company_location_id, id, location_id, company_description, company_email, company_name, name, phone_number, profile_picture, surname)
VALUES
    (0, '18:00:00', '09:00:00', '4d5f7a3e-9b6c-4a2a-b8f9-7e3c9b5a2d6f', 'a7c9e5b3-d4f2-49a1-b8c7-3e7f9a5b2c6d', '7d3f9e6a-b2c5-4a9b-b8f7-4c9e5a7d3b2f', 'Tech solutions for small businesses', 'info@techcorp.com', 'TechCorp', 'John', '+1234567890', 'https://example.com/john.jpg', 'Doe'),
    (1, '17:00:00', '08:00:00', '9f7e6a3c-b4d5-4b1a-b2c9-7e3f8d5b2a6c', 'c7a9e5d3-f2b4-4a1b-b8c6-3f9e7a5b2d4f', '7e5f4a3c-d9b7-4b2a-b6c8-3e9a7d5f2b6c', 'Providing top-tier consultancy services', 'contact@bizconsult.com', 'BizConsult', 'Alice', '+9876543210', 'https://example.com/alice.jpg', 'Smith'),
    (2, '20:00:00', '10:00:00', '5c9e8a3d-b2f7-4a1a-b8c1-7f3e9d6b4a7c', 'd7b5e9c3-a2f4-49b8-b6c1-3f9a7e5b2c8d', '8f6c9e3a-d7b5-4a2c-b1f6-3e7a9d5b4c6f', 'Offering creative solutions for marketing', 'support@creativehub.com', 'CreativeHub', 'Emma', '+1122334455', 'https://example.com/emma.jpg', 'Brown');


INSERT INTO categories (is_deleted, status, id, description, name)
VALUES
    (FALSE, 0, 'b1d5d5c5-f6c1-4039-a8a5-4fc3ea0a4e2a', 'Category for technology', 'Technology'),
    (FALSE, 1, 'd4f4e6b7-d2d5-4376-8a9b-7c4f3b3c1e7d', 'Category for arts and culture', 'Arts'),
    (TRUE, 2, 'c7d2b4f3-8c4f-432e-8b5c-9a1d3f1b8d5a', 'Deprecated category', 'Legacy');


INSERT INTO reports ("timestamp", id, reported_id, reporter_id, reason)
VALUES
    ('2024-12-07 10:00:00', 'a3d4b5e6-c7f8-4a9a-b2c1-3f9e7a5b2d6f', 'd7b9e5c3-a6f4-49a2-b8c1-7e3f9a2d6b4f', '4b9c7f5a-d3e2-42a1-b6c8-3f7e9d5a2c6f', 'Inappropriate behavior'),
    ('2024-12-07 10:15:00', 'b6e7f8a3-c2d4-4a5b-b9c1-7d3f9e6a2b5c', '4b9c7f5a-d3e2-42a1-b6c8-3f7e9d5a2c6f', 'b7c9e2d4-a5b3-49c8-b2f7-d4a1c7e6f2d3', 'Spam content'),
    ('2024-12-07 10:30:00', 'c7a9b8e5-d2f3-4b1a-b9c6-7e5f4c3a8b2d', 'b7c9e2d4-a5b3-49c8-b2f7-d4a1c7e6f2d3', 'd7b9e5c3-a6f4-49a2-b8c1-7e3f9a2d6b4f', 'Harassment');


INSERT INTO messages ("timestamp", from_id, id, to_id, content)
VALUES
    ('2024-12-07 09:00:00', 'ecf3b2b1-d3f4-46e8-92b1-43a21b60c2b3', 'b2c9e3a5-d4f7-4b8f-b7e1-3f9c6a2d5b7e', 'dab3e1b5-7a4e-49b6-9c72-34a2d512f8a3', 'Hello, how are you?'),
    ('2024-12-07 09:05:00', 'dab3e1b5-7a4e-49b6-9c72-34a2d512f8a3', 'e5c7b3a9-d2f6-4a1a-b8c9-7f3e6b2d9a4f', 'ecf3b2b1-d3f4-46e8-92b1-43a21b60c2b3', 'I am good, thank you!'),
    ('2024-12-07 09:10:00', 'b4c92e3f-d2b7-42a5-a3c1-9d7e5b6a4f8c', '7e5a3c9d-48f1-432a-b6c1-3e7a8f5b2d9e', '3b9e6a2c-d7f2-4a3b-b2c9-7e5f4b6a1c8d', 'See you at the event.');


INSERT INTO blocks ("timestamp", blocked_id, id, who_id)
VALUES
    ('2024-12-07 15:00:00', 'f0d6c6ea-14bf-4974-8c3e-482d5e66f34f', '6a0e2d7c-0c6b-4bb2-a6b2-b9e2583a171b', 'f4bb3025-88e7-4dc8-835e-6fda3279f8c8'),
    ('2024-12-07 16:00:00', '2c9ec448-3567-4bb3-81e4-158bdc9346c1', 'ad9a3e51-c0ef-4556-8c13-d7ad0b0c9164', '5db5b1b6-3a15-40b5-a8ec-72e7e62a8fbf'),
    ('2024-12-07 17:00:00', '857bf6d2-7d0d-41a9-8104-9ed3b85c2d1f', 'e7a214b8-39a5-4e6f-844b-d5f2a01ef3da', '9dbedc2e-4d54-4c4b-902b-49f3f1d6bda5');


INSERT INTO category_event_types (category_id, event_type_id)
VALUES
    ('b1d5d5c5-f6c1-4039-a8a5-4fc3ea0a4e2a', '7d5f3c8a-4b2d-49e6-8c3b-9a7d5e6c1b5d'),
    ('d4f4e6b7-d2d5-4376-8a9b-7c4f3b3c1e7d', '4c1f2b3a-9d5c-41f8-832e-7b6d5a3c4b9f'),
    ('c7d2b4f3-8c4f-432e-8b5c-9a1d3f1b8d5a', '9b5d3e6a-7c4f-41f8-a8c2-4d3a9e5b6d2f');


INSERT INTO company_pictures (service_provider_id, picture_url)
VALUES
    ('f9e2d6b3-9a5c-4b6a-b8f3-d4e72c9a5b1c', 'https://example.com/picture1.jpg'),
    ('7e4c5b92-3f1a-49d8-b2c6-5d7e8b6a3f1c', 'https://example.com/picture2.jpg'),
    ('3b7f2e5c-6d1a-42c9-b8f5-7d9e4b6c3a2f', 'https://example.com/picture3.jpg');


INSERT INTO events (max_attendance, privacy, "time", event_type_id, event_organizer_id, id, location_id, description, name, picture)
VALUES
    (100, 0, '2024-12-10 10:00:00', 'c7a5f4b9-d2e3-4a1a-b6f5-3c9e8d7b2a4c', 'b7c9e2d4-a5b3-49c8-b2f7-d4a1c7e6f2d3', '3f7b2c9e-4a6f-4d5b-b8c1-7a2f9e3b6d4a', '7c5f3e9a-d8b1-4a2c-b9f6-5a3e7d2b8c4f', 'Annual gathering', 'Tech Meetup', 'https://example.com/tech.jpg'),
    (200, 1, '2024-12-15 18:00:00', 'a7c5e2b9-d3f4-49b8-b6c1-3f9e7a4d5b2c', '3f2b7e9a-6d4c-4b8f-b2a1-5c7e3d9f6b2a', '2d4a7c9e-6f3b-42a1-b8f5-3c7e9b6a4d5f', '9e5c3a7f-6d4b-4b9a-b1c8-7a3f2e9b5d6c', 'Holiday party', 'Winter Fest', 'https://example.com/winter.jpg'),
    (NULL, 0, '2024-12-20 14:00:00', '4e2d5b9f-a6c3-49a1-b8f5-7d9c7b6e3a2c', '4b9c7f5a-d3e2-42a1-b6c8-3f7e9d5a2c6f', '4b3a7e9c-d8f5-49a1-b2c7-5a9d7f6e3c2b', '3c7f9e5b-6a2d-42a9-b8c5-7d2f3b9e6a4f', 'Local festival', 'Spring Fair', 'https://example.com/spring.jpg');


INSERT INTO persons_attending_events (event_id, person_id)
VALUES
    ('a7c5e2b9-d3f4-49b8-b6c1-3f9e7a4d5b2c', 'd7b9e5c3-a6f4-49a2-b8c1-7e3f9a2d6b4f'),
    ('4e2d5b9f-a6c3-49a1-b8f5-7d9c7b6e3a2c', '4b9c7f5a-d3e2-42a1-b6c8-3f7e9d5a2c6f'),
    ('b6f3e9d2-c7a5-4b1a-b2c9-8d5e7c6f3a2b', 'b7c9e2d4-a5b3-49c8-b2f7-d4a1c7e6f2d3');


INSERT INTO persons_favorite_events (event_id, person_id)
VALUES
    ('a7c5e2b9-d3f4-49b8-b6c1-3f9e7a4d5b2c', 'd7b9e5c3-a6f4-49a2-b8c1-7e3f9a2d6b4f'),
    ('b6f3e9d2-c7a5-4b1a-b2c9-8d5e7c6f3a2b', '4b9c7f5a-d3e2-42a1-b6c8-3f7e9d5a2c6f'),
    ('4e2d5b9f-a6c3-49a1-b8f5-7d9c7b6e3a2c', 'b7c9e2d4-a5b3-49c8-b2f7-d4a1c7e6f2d3');


INSERT INTO invitations (status, "timestamp", event_id, id, picture, target_email)
VALUES
    (0, '2024-12-08 12:00:00', 'a7c5e2b9-d3f4-49b8-b6c1-3f9e7a4d5b2c', 'f5b2d3c9-7e6a-49a1-b8c5-3e7f9d2a4b6c', 'https://example.com/invitation1.jpg', 'attendee1@example.com'),
    (1, '2024-12-09 15:30:00', 'b6f3e9d2-c7a5-4b1a-b2c9-8d5e7c6f3a2b', '7f2a3b9e-6d4c-4b8f-b2a1-5c7e3d9f6b2a', 'https://example.com/invitation2.jpg', 'attendee2@example.com'),
    (2, '2024-12-10 20:00:00', '4e2d5b9f-a6c3-49a1-b8f5-7d9c7b6e3a2c', '3c9a7b2e-4d5f-4a8b-b1c7-5f3e2d9b6a4c', 'https://example.com/invitation3.jpg', 'attendee3@example.com');


INSERT INTO prices (base_price, discount, final_price, "timestamp", id)
VALUES
    (100.00, 10.00, 90.00, '2024-12-07 07:30:00', '5d2f4a3b-c7a9-4e1a-b8f6-3e7a5c9d2b4f'),
    (200.00, 20.00, 160.00, '2024-12-07 07:45:00', 'c7e5a3b9-d2f4-49b8-b1c9-5f7a3d9e6b4c'),
    (300.00, 0.00, 300.00, '2024-12-07 08:00:00', '9d6b7c5e-a4f2-4a9b-b6c1-3e5f7d2b8a4f');


INSERT INTO agenda_activities (end_time, start_time, event_id, id, description, location_name, name)
VALUES
    ('2024-12-07 15:30:00', '2024-12-07 14:30:00', 'a2b9d7c8-5f7c-41b2-8b6a-7f7e4b9d8c6a', 'b9a7f3d9-c5e4-4a2a-b1e8-a9f7b2c9d8f4', 'Keynote speech by the CEO', 'Main Hall', 'Opening Ceremony'),
    ('2024-12-07 17:00:00', '2024-12-07 16:00:00', 'c8d9b6f7-3a6f-4d2a-b1c6-5a7d6e9b7c8a', 'e9d2c5f7-3e7f-4a6d-b1f7-d9b5e7d2a8b9', 'Networking session with industry leaders', 'Conference Room 1', 'Networking Session'),
    ('2024-12-07 18:30:00', '2024-12-07 17:30:00', 'b5e9a7d6-1d8c-4b9a-9d5b-7f8e2c9b7d9b', 'a8e7d6f9-b5f8-42d2-9a7b-5d9f7c8d9b6a', 'Panel discussion on emerging trends in technology', 'Conference Room 2', 'Panel Discussion');


INSERT INTO products (cancellation_window_days, duration_minutes, is_auto_accept, is_available, is_deleted, is_visible, reservation_window_days, status, edit_timestamp, category_id, id, service_provider_id, type, description, name)
VALUES
    (3, 120, true, true, false, true, 5, 1, '2024-12-07 09:30:00', 'c8d9b6f7-3a6f-4d2a-b1c6-5a7d6e9b7c8a', 'a7c9e5b3-d4f2-49a1-b8c7-3e7f9a5b2c6d', 'service', 'A professional IT consultation', 'Tech Consultation'),
    (5, 180, false, true, false, true, 7, 0, '2024-12-07 09:45:00', '9f8e7c3a-4b6f-4d2a-b5e1-7a3f9c6e2b4d', 'c7a9e5d3-f2b4-4a1b-b8c6-3f9e7a5b2d4f', 'product', 'A cleaning service for offices', 'Office Cleaning'),
    (10, 90, true, false, false, false, 3, 2, '2024-12-07 10:00:00', '7e5f4c2a-d8b9-4b3a-b1f6-3c7a8f5b2d9e', 'c7a9e5d3-f2b4-4a1b-b8c6-3f9e7a5b2d4f', 'service', 'A professional cleaning service for homes', 'Home Cleaning');


INSERT INTO reservations (end_time, start_time, "timestamp", event_id, id, product_id)
VALUES
    ('2024-12-07 15:30:00', '2024-12-07 14:30:00', '2024-12-07 09:00:00', 'a2b9d7c8-5f7c-41b2-8b6a-7f7e4b9d8c6a', 'b9a7f3d9-c5e4-4a2a-b1e8-a9f7b2c9d8f4', 'a2b9d7c8-5f7c-41b2-8b6a-7f7e4b9d8c6a'),
    ('2024-12-07 17:00:00', '2024-12-07 16:00:00', '2024-12-07 09:30:00', 'c8d9b6f7-3a6f-4d2a-b1c6-5a7d6e9b7c8a', 'e9d2c5f7-3e7f-4a6d-b1f7-d9b5e7d2a8b9', 'c8d9b6f7-3a6f-4d2a-b1c6-5a7d6e9b7c8a'),
    ('2024-12-07 18:30:00', '2024-12-07 17:30:00', '2024-12-07 10:00:00', 'b5e9a7d6-1d8c-4b9a-9d5b-7f8e2c9b7d9b', 'a8e7d6f9-b5f8-42d2-9a7b-5d9f7c8d9b6a', 'b5e9a7d6-1d8c-4b9a-9d5b-7f8e2c9b7d9b');


INSERT INTO ratings (value, event_organizer_id, id, product_id)
VALUES
    (4, 'c8d9b6f7-3a6f-4d2a-b1c6-5a7d6e9b7c8a', 'a1b3d5f7-9d7c-4d2a-b6d7-e8a3f2d9b9f5', 'a2b9d7c8-5f7c-41b2-8b6a-7f7e4b9d8c6a'),
    (5, 'b7c9e2d4-a5b3-49c8-b2f7-d4a1c7e6f2d3', 'f4d5b6e9-8d3b-47c6-b8a7-d9c3e8b5d9b6', 'c8d9b6f7-3a6f-4d2a-b1c6-5a7d6e9b7c8a'),
    (3, 'd8b9f5e1-7e4a-41c6-b2d3-d9a7f9c8e9b2', 'a7f9c5d6-b9e1-4a7a-b8e6-f9b2a8d5c6b4', 'b5e9a7d6-1d8c-4b9a-9d5b-7f8e2c9b7d9b');


INSERT INTO product_prices (price_id, product_id)
VALUES
    ('4d5e6c8a-1f9b-42a9-b1d6-e9c8f7d3a2b9', 'a2b9d7c8-5f7c-41b2-8b6a-7f7e4b9d8c6a'),
    ('5f6e9d2a-8d3b-47f9-b6c8-9e5a3d2c9b8a', 'c8d9b6f7-3a6f-4d2a-b1c6-5a7d6e9b7c8a'),
    ('3e5f6d9b-7c8e-49a3-a2b6-d8e7f5a9b6c3', 'b5e9a7d6-1d8c-4b9a-9d5b-7f8e2c9b7d9b');


INSERT INTO product_event_types (event_type_id, product_id)
VALUES
    ('1d2b5f9e-7d9c-4a4f-9a6b-7d9c6f5a2b9a', 'a2b9d7c8-5f7c-41b2-8b6a-7f7e4b9d8c6a'),
    ('7d3b6f9e-9e7f-4d5a-b9d6-c9f8b7d5e2c9', 'c8d9b6f7-3a6f-4d2a-b1c6-5a7d6e9b7c8a'),
    ('2c9f5b7e-6b3c-47d6-b7e1-d9a6f2d7c9b3', 'b5e9a7d6-1d8c-4b9a-9d5b-7f8e2c9b7d9b');


INSERT INTO product_pictures (product_id, picture_url)
VALUES
    ('a2b9d7c8-5f7c-41b2-8b6a-7f7e4b9d8c6a', 'https://example.com/images/product1.jpg'),
    ('c8d9b6f7-3a6f-4d2a-b1c6-5a7d6e9b7c8a', 'https://example.com/images/product2.jpg'),
    ('b5e9a7d6-1d8c-4b9a-9d5b-7f8e2c9b7d9b', 'https://example.com/images/product3.jpg');


INSERT INTO persons_favorite_products (person_id, product_id)
VALUES
    ('d7b9e5c3-a6f4-49a2-b8c1-7e3f9a2d6b4f', 'a9237b4a-6c5e-4e71-b3c8-9d6e435d2e21'),
    ('4b9c7f5a-d3e2-42a1-b6c8-3f7e9d5a2c6f', '34f1b7e2-5c9a-4d2a-b6c1-7f3e8b9d5a2c'),
    ('b7c9e2d4-a5b3-49c8-b2f7-d4a1c7e6f2d3', '7e5a3c9d-48f1-432a-b6c1-3e7a8f5b2d9e');


INSERT INTO comments (status, author_id, id, product_id, content)
VALUES
    (0, 'ecf3b2b1-d3f4-46e8-92b1-43a21b60c2b3', 'e52d2a61-abc7-42e9-82d4-7b3f52a4a1c5', 'a9237b4a-6c5e-4e71-b3c8-9d6e435d2e21', 'Great product!'),
    (1, 'dab3e1b5-7a4e-49b6-9c72-34a2d512f8a3', 'f92c4b63-d2a1-49d7-8f1e-9b4c72e1a5f3', '7e5a3c9d-48f1-432a-b6c1-3e7a8f5b2d9e', 'Needs improvement.'),
    (2, 'b4c92e3f-d2b7-42a5-a3c1-9d7e5b6a4f8c', '3b9e6a2c-d7f2-4a3b-b2c9-7e5f4b6a1c8d', '34f1b7e2-5c9a-4d2a-b6c1-7f3e8b9d5a2c', 'Highly recommended!');


INSERT INTO notifications ("timestamp", event_id, id, product_id, content)
VALUES
    ('2024-12-07 08:00:00', 'a7c5e2b9-d3f4-49b8-b6c1-3f9e7a4d5b2c', 'c7a5f4b9-d2e3-4a1a-b6f5-3c9e8d7b2a4c', NULL, 'Event reminder: Tech Meetup'),
    ('2024-12-07 08:15:00', NULL, '7c5f3e9a-d8b1-4a2c-b9f6-5a3e7d2b8c4f', '9f7e6c3a-4d8b-4a1a-b2c5-3e7f9b6d4a5c', 'New product available: Widget X'),
    ('2024-12-07 08:30:00', '4e2d5b9f-a6c3-49a1-b8f5-7d9c7b6e3a2c', '3c7f9e5b-6a2d-42a9-b8c5-7d2f3b9e6a4f', 'b6f3e9d2-c7a5-4b1a-b2c9-8d5e7c6f3a2b', 'Event cancelled: Spring Fair');


INSERT INTO persons_notifications (notification_id, person_id)
VALUES
    ('c7a5f4b9-d2e3-4a1a-b6f5-3c9e8d7b2a4c', 'd7b9e5c3-a6f4-49a2-b8c1-7e3f9a2d6b4f'),
    ('7c5f3e9a-d8b1-4a2c-b9f6-5a3e7d2b8c4f', '4b9c7f5a-d3e2-42a1-b6c8-3f7e9d5a2c6f'),
    ('3c7f9e5b-6a2d-42a9-b8c5-7d2f3b9e6a4f', 'b7c9e2d4-a5b3-49c8-b2f7-d4a1c7e6f2d3');

