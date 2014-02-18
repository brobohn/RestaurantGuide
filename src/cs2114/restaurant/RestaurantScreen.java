package cs2114.restaurant;

import sofia.content.ContentViewer;
import android.net.Uri;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;
import realtimeweb.yelp.Business;
import realtimeweb.yelp.BusinessQuery;
import realtimeweb.yelp.BusinessSearch;
import realtimeweb.yelp.BusinessSearchListener;
import realtimeweb.yelp.SearchResponse;
import realtimeweb.yelp.exceptions.BusinessSearchException;
import sofia.app.Screen;
import sofia.widget.ImageView;

// -------------------------------------------------------------------------
/**
 * This screen pulls data from yelp and displays it using a CircularLinkedList.
 *
 * @author Benjamin Robohn (brobohn)
 * @version 2013.11.13
 */
public class RestaurantScreen
    extends Screen
    implements BusinessSearchListener
{
    // ~ Fields ................................................................

    private BusinessSearch               searcher;
    private CircularLinkedList<Business> list;
    // GUI elements
    private EditText                     searchField;
    private Button                       next;
    private Button                       previous;
    private Button                       viewMap;
    private RatingBar                    ratingBar;
    private ImageView                    imageView;
    private TextView                     restaurantName;
    private TextView                     numRatings;


    // ----------------------------------------------------------
    @Override
    public void initialize()
    {
        list = new CircularLinkedList<Business>();
        searcher = BusinessSearch.getInstance();
    }


    // ----------------------------------------------------------
    /**
     * Starts a search based on the city entered.
     */
    public void searchFieldEditingDone()
    {
        searcher.searchBusinesses(new BusinessQuery(searchField.getText()
            .toString()), new BusinessSearchGUIAdapter(this));
    }


    /**
     * Called when the search is complete.
     *
     * @param response
     *            the response from the business search
     */
    @Override
    public void businessSearchCompleted(SearchResponse response)
    {
        list.clear();

        for (int i = response.getBusinesses().size() - 1; i >= 0; i--)
        {
            list.add(response.getBusinesses().get(i));
        }

        if (list.size() == 0)
        {
            next.setEnabled(false);
            previous.setEnabled(false);
            viewMap.setEnabled(false);
        }
        else

        {
            next.setEnabled(true);
            previous.setEnabled(true);
            viewMap.setEnabled(true);

            updateScreen(list.getCurrent());
        }
    }


    // ----------------------------------------------------------
    /**
     * Loads information about the current business onto the screen
     *
     * @param bus
     *            the current Business
     */
    public void updateScreen(Business bus)
    {
        imageView.setImageURI(null);
        String uriString = bus.getImageUrl();
        if (uriString != null)
        {
            imageView.setImageURI(Uri.parse(uriString));
        }
        restaurantName.setText(bus.getName());
        ratingBar.setRating(bus.getRating());
        numRatings.setText(bus.getReviewCount() + " ratings");
    }


    // ----------------------------------------------------------
    /**
     * Moves to the next item in the list.
     */
    public void nextClicked()
    {
        list.next();
        updateScreen(list.getCurrent());
    }


    /**
     * Moves to the next item in the list.
     */
    public void previousClicked()
    {
        list.previous();
        updateScreen(list.getCurrent());
    }


    /**
     * Shows the location in the browser.
     */
    public void viewMapClicked()
    {
        String uriString =
            "http://maps.google.com/maps?q="
                + list.getCurrent().getLocation().getLatitude() + ","
                + list.getCurrent().getLocation().getLongitude();
        new ContentViewer(Uri.parse(uriString)).start(this);
    }


    /**
     * `Called if there is a problem retrieving data
     *
     * @param exc
     *            the exception thrown from a failed search.
     */
    @Override
    public void businessSearchFailed(BusinessSearchException exc)
    {
        next.setEnabled(false);
        previous.setEnabled(false);
        viewMap.setEnabled(false);
    }

}
