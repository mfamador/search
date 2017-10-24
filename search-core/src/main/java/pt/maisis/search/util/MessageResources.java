package pt.maisis.search.util;

import java.text.MessageFormat;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Immutable Resources Bundle class that helps getting messages from the
 * default, or specified, resources bundle.
 *
 * @version 1.0
 */
public class MessageResources {

    private static Log log = LogFactory.getLog(MessageResources.class);
    private static final MessageResources me =
            new MessageResources("search-core");
    private ResourceBundle[] resources;

    /**
     * Returns an instance of this class, with the default configuration.
     */
    public static MessageResources getInstance() {
        return me;
    }

    /**
     * Returns an instance of this class, taking in count the given locale and
     * name of the specified resource bundle.
     */
    public static MessageResources getInstance(final Locale locale,
            final String resources) {
        return new MessageResources(locale, resources);
    }

    /**
     * Creates an instance of this class.
     */
    private MessageResources(final String resources) {
        this(Locale.getDefault(), resources);
    }

    /**
     * Creates an instance of this class.
     */
    private MessageResources(final Locale locale,
            final String resources) {
        try {
            String[] temp = resources.split(",");
            this.resources = new ResourceBundle[temp.length];

            for (int i = 0; i < temp.length; i++) {
                this.resources[i] = ResourceBundle.getBundle(temp[i].trim(), locale);
            }
        } catch (MissingResourceException e) {
            log.error("[resources bundle]: " + e.getMessage());
        }
    }

    /**
     * Returns the message for the given key. If the key is not found in the
     * resources bundle, the key is returned.
     */
    public String getMessage(final String key) {
        if (key == null) {
            return null;
        }

        for (int i = 0; i < this.resources.length; i++) {
            if (this.resources[i].containsKey(key)) {
                return this.resources[i].getString(key);
            }
        }
        
        return key;
    }

    public String getMessage(final String key,
            final Object arg1) {
        return getMessage(key, new Object[]{arg1});
    }

    public String getMessage(final String key,
            final Object arg1,
            final Object arg2) {
        return getMessage(key, new Object[]{arg1, arg2});
    }

    public String getMessage(final String key,
            final Object arg1,
            final Object arg2,
            final Object arg3) {
        return getMessage(key, new Object[]{arg1, arg2, arg3});
    }

    public boolean contains(final String key) {
        if (key == null) {
            return false;
        }

        for (int i = 0; i < this.resources.length; i++) {
            if (this.resources[i].containsKey(key)) {
                return true;
            }
        }

        return false;
    }

    public String getMessage(final String key,
            final Object[] args) {

        if (key == null) {
            return null;
        }

        for (int i = 0; i < this.resources.length; i++) {
            if (this.resources[i].containsKey(key)) {
                return MessageFormat.format(this.resources[i].getString(key), args);
            }
        }
        
        return key;
    }
}
