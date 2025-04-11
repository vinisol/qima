-- Root Categories (Depth 1)
INSERT INTO category (name, parent_id, created_at, updated_at) VALUES
('Books', NULL, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('Tools', NULL, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('Beauty', NULL, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- Depth 2
INSERT INTO category (name, parent_id, created_at, updated_at) VALUES
('Fiction', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('Non-Fiction', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('Power Tools', 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('Skin Care', 3, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- Depth 3
INSERT INTO category (name, parent_id, created_at, updated_at) VALUES
('Fantasy', 4, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('Biography', 5, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('Drills', 6, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('Moisturizers', 7, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- Depth 4
INSERT INTO category (name, parent_id, created_at, updated_at) VALUES
('Epic Fantasy', 8, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('Political Biography', 9, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('Cordless Drills', 10, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('Night Moisturizers', 11, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- Products
INSERT INTO product (name, description, price, available, sku, category_id, created_at, updated_at) VALUES
-- Depth 1 categories
('Generic Book Light', 'LED light for reading', 9.99, true, 'GEN-BKLIGHT', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('Universal Tool Bag', 'Bag to hold all tools', 19.99, true, 'GEN-TOOLBAG', 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('Makeup Organizer', 'Portable makeup case', 14.99, true, 'GEN-BEAUTYBOX', 3, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

-- Depth 2 categories
('The Great Novel', 'Bestselling fiction book', 12.99, true, 'FICTION-NOVEL', 4, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('Productivity Hacks', 'Non-fiction productivity tips', 15.00, true, 'NONFIC-PROD', 5, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('Hammer Drill', 'Powerful tool for concrete', 89.99, false, 'TOOL-HDRILL', 6, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('Facial Cleanser', 'Deep cleansing face wash', 7.99, true, 'BEAUTY-CLEANSE', 7, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

-- Depth 3 categories
('Elven Chronicles', 'Fantasy adventure saga', 17.99, true, 'FANTASY-ELVEN', 8, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('Steve Jobs Bio', 'Biography of Steve Jobs', 19.99, false, 'BIO-SJOBS', 9, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('Corded Drill', 'Electric drill with cord', 29.99, true, 'DRILL-CORDED', 10, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('Daily Face Cream', 'Hydrating moisturizer', 11.99, false, 'MOIST-DAILY', 11, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

-- Depth 4 categories
('Lord of the Rings', 'Epic fantasy trilogy', 25.00, false, 'EPIC-LOTR', 12, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('The Iron Lady', 'Margaret Thatcher bio', 16.50, true, 'BIO-THATCHER', 13, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('18V Cordless Drill', 'Rechargeable cordless drill', 79.99, false, 'DRILL-18V', 14, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('Overnight Moisturizer', 'Nighttime facial treatment', 22.50, true, 'MOIST-NIGHT', 15, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

-- Extra products to fill 30
('Sword of Kings', 'Epic fantasy weapon replica', 120.00, true, 'EPIC-SWORD', 12, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('Tool Cleaning Kit', 'Maintain your drills', 15.99, true, 'DRILL-KIT', 10, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('Gentle Skin Wash', 'Moisturizer-friendly soap', 6.99, false, 'MOIST-WASH', 11, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('Book Set: Elven Tales', 'Boxed set of fantasy stories', 32.00, true, 'FANTASY-BOXSET', 8, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('Biography Collection', '3-book bundle', 28.99, true, 'BIO-BUNDLE', 9, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('Quick Dry Drill', 'Compact cordless model', 55.00, false, 'DRILL-FAST', 14, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('Night Repair Serum', 'Moisturizer booster', 19.99, true, 'MOIST-SERUM', 15, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('Fantasy Poster', 'Art from epic books', 10.00, true, 'EPIC-POSTER', 12, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('Compact Drill Case', 'Organize your power tools', 24.99, true, 'DRILL-CASE', 10, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('Facial Mask Set', 'Nighttime treatment combo', 18.75, false, 'MOIST-MASK', 15, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('Desk Lamp', 'Perfect for nighttime reading', 13.49, true, 'GEN-DESKLIGHT', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('Workshop Goggles', 'Protective gear for tools', 8.99, true, 'TOOL-GOGGLES', 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('Blush Kit', 'All-in-one beauty blush set', 16.00, true, 'BEAUTY-BLUSH', 3, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('The Minimalist Guide', 'Non-fiction book', 13.95, true, 'NONFIC-MIN', 5, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('Hydra Boost Gel', 'Intensive face hydration', 21.25, true, 'MOIST-HYDRAGEL', 11, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
