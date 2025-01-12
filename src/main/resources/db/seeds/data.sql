INSERT INTO public.warehouses (id, location, status, type, latitude, longitude) VALUES (4, 'Some Address 4', 'Ok', 'Well', 49.4146100000, 8.6814950000);
INSERT INTO public.warehouses (id, location, status, type, latitude, longitude) VALUES (1, '2700 Regent Blvd, Irving, TX 75063, United States', 'Ok', 'Well', 32.9401342420, -97.0292188840);
INSERT INTO public.warehouses (id, location, status, type, latitude, longitude) VALUES (2, '1555 N Chrisman Rd, Tracy, CA 95304, United States', 'Ok', 'Well', 37.7453655138, -121.4048990607);
INSERT INTO public.warehouses (id, location, status, type, latitude, longitude) VALUES (3, '1901 140th Ave E, Sumner, WA 98390, United States', 'Ok', 'Well', 47.2406304286, -122.2409355640);
INSERT INTO public.warehouses (id, location, status, type, latitude, longitude) VALUES (5, '20202 84th Ave S, Kent, WA 98032, United States', 'Ok', 'Well', 47.4198133781, -122.2267361734);
INSERT INTO public.warehouses (id, location, status, type, latitude, longitude) VALUES (6, '23226 Witte Rd SE, Maple Valley, WA 98038', 'Ok', 'Well', 47.3912062957, -122.0419501566);

INSERT INTO public.products (height, length, price, weight, width, id, category, description, name) VALUES (1, 1, 1, 1, 1, 1, '1', '1', 'Bread');
INSERT INTO public.products (height, length, price, weight, width, id, category, description, name) VALUES (1, 1, 1, 1, 1, 2, '1', '1', 'Coca-cola');
INSERT INTO public.products (height, length, price, weight, width, id, category, description, name) VALUES (1, 1, 1, 1, 1, 3, '1', '1', 'T-Short');

INSERT INTO public.inventory (quantity, id, product_id, warehouse_id) VALUES (50, 1, 1, 1);
INSERT INTO public.inventory (quantity, id, product_id, warehouse_id) VALUES (150, 2, 2, 2);
INSERT INTO public.inventory (quantity, id, product_id, warehouse_id) VALUES (500, 3, 1, 3);
INSERT INTO public.inventory (quantity, id, product_id, warehouse_id) VALUES (1000, 5, 3, 5);
INSERT INTO public.inventory (quantity, id, product_id, warehouse_id) VALUES (1000, 7, 3, 3);
INSERT INTO public.inventory (quantity, id, product_id, warehouse_id) VALUES (1000, 8, 3, 6);

INSERT INTO drones (id, battery_level, capacity, load, next_available_time, status, warehouse_id)
VALUES
(1, 85.5, 10.0, 0.0, '2024-11-25 10:00:00', 'available', 5),
(2, 50.0, 8.0, 4.0, '2024-11-25 12:00:00', 'available', 5),
(3, 30.0, 12.0, 6.0, '2024-11-25 15:00:00', 'on_way', 5),
(4, 90.0, 15.0, 0.0, '2024-11-25 09:00:00', 'available', 5);

INSERT INTO vans (id, capacity, load, next_available_time, status, warehouse_id)
VALUES
(1, 10.0, 0.0, '2024-11-25 10:00:00', 'available', 5),
(2, 8.0, 4.0, '2024-11-25 12:00:00', 'available', 5),
(3, 12.0, 6.0, '2024-11-25 15:00:00', 'in_flight',5),
(4, 15.0, 0.0, '2024-11-25 09:00:00', 'available', 5);