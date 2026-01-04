package samples.webapps.bookstore.bookstore2.web.messages;

import java.util.*;

public class BookstoreMessages extends ListResourceBundle {
  public Object[][] getContents() {
    return contents;
  }

  static final Object[][] contents = {

  {"ServerError", "Your request cannot be completed.  The server got the following error: "},
  {"TitleServerError", "Server Error"},
  {"TitleShoppingCart", "Shopping Cart"},
  {"TitleReceipt", "Receipt"},
  {"TitleBookCatalog", "Book Catalog"},
  {"TitleCashier", "Cashier"},
  {"TitleBookDescription", "Book Description"},
  {"What", "What We\'re Reading"},
  {"Talk", " talks about how web components can transform the way you develop applications for the web. This is a must read for any self respecting web developer!"},
  {"Start", "Start Shopping"},
  {"Critics", "Here's what the critics say: "},
  {"Price", "Our Price: "},
  {"CartRemoved", "You just removed "},
  {"CartCleared", "You just cleared your shopping cart!"},
  {"CartContents", "Your shopping cart contains "},
  {"CartItem", " item"},
  {"CartItems", " items"},
  {"CartAdded1", "You added "},
  {"CartAdded2", " to your shopping cart."},
  {"CartCheck", "Check Shopping Cart"},
  {"CartAdd", "Add to Cart"},
  {"By", "by"},
  {"Buy", "Buy Your Books"},
  {"Choose", "Please choose from our selections:"},
  {"ItemQuantity", "Quantity"},
  {"ItemTitle", "Title"},
  {"ItemPrice", "Price"},
  {"RemoveItem", "Remove Item"},
  {"Subtotal", "Subtotal:"},
  {"ContinueShopping", "Continue Shopping"},
  {"Checkout", "Check Out"},
  {"ClearCart", "Clear Cart"},
  {"CartEmpty", "Your cart is empty."},
  {"Amount", "Your total purchase amount is:"},
  {"Purchase", "To purchase the items in your shopping cart, please provide us with the following information:"},
  {"Name", "Name:"},
  {"CCNumber", "Credit Card Number:"},
  {"Submit", "Submit Information"},
  {"Catalog", "Back to the Catalog"},
  {"ThankYou", "Thank you for purchasing your books from us "},
  };
}

