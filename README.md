# OnlineShopTest
A test assignment from a campaign in which it was required to create an online store
## Write a simple store API. The functionality of the API is divided into admin and user. The following rules work for the store:
* The admin functionality extends all the mentioned user functionality;
* Admin can add and change any information about products in the store;
* **Product information consists of:**
  * Title;
  * Description;
  * Organizations;
  * Prices;
  * Stock quantity;
  * Discount Information;
  * Reviews;
  * Keywords;
  * Features tables;
  * Ratings.
* For a product or group of products the administrator can add and change discounts;
* **Discount information consists of:**
  * The goods involved;
  * The amount of discount;
  * The duration of the discount.
* In order to use the store, the user must be registered and log in to the account;
* The user can buy products, leave comments and ratings about them;
* Each purchase must be saved in the purchase history;
* The user can view their purchase history;
* Admin can view any user's purchase history;
* The user can make a return within 24 hours of purchase;
* User can't rate or leave feedback on a product without buying it;
* **User information consists of:**
  * Username;
  * E-mail;
  * Password;
  * Balance.
* The administrator can top up the user's balance;
* Admin can see information about users, delete and freeze their accounts;
* Admin can send notifications to users;
* **Notifications consist of:**
  * Notification Header;
  * The date of the notification;
  * The text content of the notification.
* Users can view their notifications
* The user can apply to register an organization;
* An organization enables its creator to add products and sell them in this store;
* **An organization consists of:**
  * Name;
  * Description
  * Logo;
  * Products.
* Since the organization registration request is added by an authorized user, the recipient of the proceeds is this user;
* Each user can create an unlimited number of organizations;
* Users who have registered their organization can add requests for registration of goods, and after moderation, they will be added to the general list of goods;
* When a user adds a product, he must specify on behalf of which organization the product is supplied;
* The organization receives the proceeds from the purchase of goods belonging to the organization, minus a commission. The commission is arbitrary (for example, 5%);
* Admin has the right to accept registration applications for the organization, freeze and delete the active organizations;
* If an organization is frozen or deleted, users should not see the items in the list of available items, however, the purchased items should retain information about the organization. I.e., even about the deleted, or rather banned organizations, the information should remain.
