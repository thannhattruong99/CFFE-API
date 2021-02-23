-- Database export via SQLPro (https://www.sqlprostudio.com/allapps.html)
-- Exported by truongtn at 23-02-2021 17:44.
-- WARNING: This file may contain descructive statements such as DROPs.
-- Please ensure that you are running the script at the proper location.


-- BEGIN TABLE dbo.Account
IF OBJECT_ID('dbo.Account', 'U') IS NOT NULL
DROP TABLE dbo.Account;
GO

CREATE TABLE dbo.Account (
                             email varchar(30) NOT NULL DEFAULT ('NULL'),
                             password varchar(30) NOT NULL,
                             fullname varchar(30) NOT NULL,
                             role int NOT NULL,
                             status int NOT NULL
);
GO

ALTER TABLE dbo.Account ADD CONSTRAINT PK__Account__AB6E6165BAFB5419 PRIMARY KEY (email);
GO

-- Inserting 4 rows into dbo.Account
-- Insert batch #1
INSERT INTO dbo.Account (email, password, fullname, role, status) VALUES
('huudn@gmail.com', 'huudn', 'Do Ngoc Huu', 1, 1),
('loctp@gmail.com', 'loctp', 'Trinh Phuc Loc', 1, 1),
('luannm@gmail.com', 'luannm', 'Nguyen Minh Luan', 1, 1),
('truongtn@gmail.com', 'truongtn', 'Than Nhat Truong', 1, 1);

-- END TABLE dbo.Account

