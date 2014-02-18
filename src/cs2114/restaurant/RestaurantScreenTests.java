package cs2114.restaurant;

import realtimeweb.yelp.exceptions.BusinessSearchException;
import android.content.Intent;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

/*
 * import sofia.widget.ImageView; import android.widget.RatingBar;
 */

// -------------------------------------------------------------------------
/**
 * Test cases for the Restaurant class.
 *
 * @author Benjamin Robohn (brobohn)
 * @version 2013.11.13
 */
public class RestaurantScreenTests
    extends student.AndroidTestCase<RestaurantScreen>
{
    // ~ Fields ................................................................
    private EditText searchField;
    private Button   next;
    private Button   previous;
    private Button   viewMap;
    /*
     * private RatingBar ratingBar; private ImageView imageView;
     */
    private TextView restaurantName;
    private TextView numRatings;


    // ~ Constructors ..........................................................

    // ----------------------------------------------------------
    /**
     * Test cases that extend AndroidTestCase must have a parameterless
     * constructor that calls super() and passes it the screen/activity class
     * being tested.
     */
    public RestaurantScreenTests()
    {
        super(RestaurantScreen.class);
    }


    // ~ Public methods ........................................................

    // ----------------------------------------------------------
    /**
     * Initializes the text fixtures.
     */
    @Override
    public void setUp()
    {
        enterText(searchField, "Blacksburg, VA");
    }


    // ----------------------------------------------------------
    /**
     * Initial test.
     */
    public void testSetUp()
    {
        assertEquals(restaurantName.getText().toString(), "Lyric Theatre");
        assertEquals(numRatings.getText().toString(), "17 ratings");
    }


    /**
     * Tests the next button
     */
    public void testNext()
    {
        click(next);
        assertEquals(restaurantName.getText().toString(), "Carol Lee Donuts");
        assertEquals(numRatings.getText().toString(), "16 ratings");
    }


    /**
     * Tests the previous button
     */
    public void testPrevious()
    {
        click(previous);
        assertEquals(restaurantName.getText().toString(), "Virginia"
            + " Polytechnic Institute and State University");
        assertEquals(numRatings.getText().toString(), "5 ratings");
    }


    /**
     * Tests viewing the map
     */
    public void testViewMap()
    {
        assertEquals(restaurantName.getText().toString(), "Lyric Theatre");
        prepareForUpcomingActivity(Intent.ACTION_VIEW);
        click(viewMap);
    }

    /**
     * Tests a null response
     */
    public void testEmptyResponse()
    {
        assertEquals(restaurantName.getText().toString(), "Lyric Theatre");
        prepareForUpcomingActivity(Intent.ACTION_VIEW);
        click(viewMap);
    }

    /**
     * Tests businessSearchFailed()
     */
    public void testBusinessSearchFailed()
    {
        getInstrumentation().runOnMainSync(new Runnable() {
            public void run()
            {
                getScreen().businessSearchFailed(
                    new BusinessSearchException("", "", ""));
            }
        });

        assertEquals(restaurantName.getText().toString(), "Lyric Theatre");
    }
}
