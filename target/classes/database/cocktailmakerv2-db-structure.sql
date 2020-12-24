CREATE TABLE cocktails (
    id INT NOT NULL AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL UNIQUE,
    PRIMARY KEY(id)
);

CREATE TABLE cocktail_images (
    cocktail_id INT NOT NULL,
    display_image BLOB NOT NULL,
    PRIMARY KEY(cocktail_id),
    FOREIGN KEY (cocktail_id) REFERENCES cocktails(id) ON DELETE CASCADE
);

CREATE TABLE ingredients (
    id INT NOT NULL AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL UNIQUE,
    alcohol_by_volume DECIMAL NOT NULL,
    PRIMARY KEY(id),
    CHECK (alcohol_by_volume >= 0 AND alcohol_by_volume <= 100)
);

CREATE TABLE pumps (
    gpio_pin TINYINT NOT NULL UNIQUE,
    ingredient_id INT NOT NULL UNIQUE,
    flow_rate SMALLINT NOT NULL,
    PRIMARY KEY(gpio_pin),
    FOREIGN KEY (ingredient_id) REFERENCES ingredients(id) ON DELETE CASCADE,
    CHECK (gpio_pin >= 0 AND gpio_pin <= 31 AND flow_rate > 0)
);

CREATE TABLE preparation_steps (
    cocktail_id INT NOT NULL,
    step_nr SMALLINT NOT NULL,
    title VARCHAR(255) NOT NULL,
    description VARCHAR(255) NOT NULL,
    PRIMARY KEY(cocktail_id, step_nr),
    FOREIGN KEY (cocktail_id) REFERENCES cocktails(id) ON DELETE CASCADE,
    CHECK (step_nr > 0)
);

CREATE TABLE followup_steps (
    cocktail_id INT NOT NULL,
    step_nr SMALLINT NOT NULL,
    title VARCHAR(255) NOT NULL,
    description VARCHAR(255) NOT NULL,
    PRIMARY KEY(cocktail_id, step_nr),
    FOREIGN KEY (cocktail_id) REFERENCES cocktails(id) ON DELETE CASCADE,
    CHECK (step_nr > 0)
);

CREATE TABLE cocktail_ingredient_relation (
    cocktail_id INT NOT NULL,
    ingredient_id INT NOT NULL,
    amount SMALLINT NOT NULL,
    FOREIGN KEY (cocktail_id) REFERENCES cocktails(id) ON DELETE CASCADE,
    FOREIGN KEY (ingredient_id) REFERENCES ingredients(id) ON DELETE RESTRICT,
    PRIMARY KEY (cocktail_id, ingredient_id)
);