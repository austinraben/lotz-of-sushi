This program simulates a restaurant from the perspective of a Manager, Host, and Server.

HOW TO RUN PROGRAM:
1. Project -> Clean... -> Clean
2. Open src/view/UserInterface.java
3. Run as Java Application

Saved Data:
- menu.txt contains the menu (duh)
- staff.txt contains Server Names and the salted and hashed password for '1. Manage'

HOW TO USE PROGRAM (please read):
1. Manage (password is 'iamthemanager123' no quotation marks)
	- Toggle Happy Hour (20% discount on APPS/DRINKS)
	- View relevant restaurant information (servers, tables, sales, tips, closed orders)
	- Hire or fire servers
	- Change password (updates staff.txt upon confirming new password)
2. Host
	- First, assign a Server to a Table (4. Assign server to table)
	- Second, seat any number of Customers at that Table (1. Seat Customers)
	- View relevant host information (view servers, tables)
	- Remove a server from a table
3. Serve
	- Enter a Server name (pre-hired servers are saved in staff.txt)
	- View the bill of your tables
	- Take orders from a table that a host assigned you to and has customers seated
	- Type in items (non-case sensitive) as they appear on the Menu
	- If the item allows for modifications, provide it (can be "none" or empty line)
	- Choose to continue with this customer, or move to the next customer at the table
	- Close order to finish the order and collect tip
	- Choose whether customers pay separately or split the bill evenly
	- Enter tip amount for each customer
4. View Menu
	- View any of the four-course menus or the full menu