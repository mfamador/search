package pt.maisis.search.web;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpServletRequest;

import java.util.Locale;

public class LocaleConfig {

    public static final String LOCALE = "pt.maisis.search.Locale";

    private LocaleConfig() {
    }

    public static void setLocale(final HttpSession session,
            final Locale locale) {
        session.setAttribute(LOCALE, locale);
    }

    public static void setLocale(final HttpServletRequest request,
            final Locale locale) {
        request.setAttribute(LOCALE, locale);
    }

    public static Locale getLocale(final HttpSession session) {
        return (Locale) session.getAttribute(LOCALE);
    }

    public static Locale getLocale(final HttpServletRequest request) {
        Locale locale = (Locale) request.getAttribute(LOCALE);

        if (locale == null) {
            HttpSession session = request.getSession(false);
            if (session != null) {
                locale = getLocale(session);
            }
            if (locale == null) {
                return request.getLocale();
            }
        }
        return locale;
    }
}
