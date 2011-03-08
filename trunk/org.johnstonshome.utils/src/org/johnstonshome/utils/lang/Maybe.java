/*
 * Licensed Materials - Property of Simon Johnston (simon@johnstonshome.org)
 * (c) Copyright Simon Johnston 2009. All rights reserved.
 * 
 * For full license details, see the file LICENSE included in the
 * distribution of this code.
 * 
 */
package org.johnstonshome.utils.lang;

/**
 * Emulates the <em>Maybe monad</em> from Haskell.
 * 
 * See <a href="http://blog.tmorris.net/maybe-in-java/">For original</a>.
 * 
 * @author Simon Johnston (simon@johnstonshome.org)
 *
 * @param <T> the type of the element wrapped in this <em>Maybe</em>.
 */
public abstract class Maybe<T> {
    
    private Maybe() {
        // nothing required
    }

    public static abstract class Nothing<T> extends Maybe<T> {
        @SuppressWarnings("synthetic-access")
        
        private Nothing() {
            // nothing required
        }
        
        /* (non-Javadoc)
         * @see java.lang.Object#toString()
         */
        @Override
        public String toString() {
            return "{Nothing}";
        }
    }

    public static abstract class Just<T> extends Maybe<T> {
        @SuppressWarnings("synthetic-access")
        private Just() {
            // nothing required
        }

        public abstract T just();
        
        /* (non-Javadoc)
         * @see java.lang.Object#toString()
         */
        @Override
        public String toString() {
            return "{Just ?}";
        }
    }
    
    public static <T> Maybe<T> create(final T t) {
        if (t == null) {
            return nothing();
        }
        return just(t);
    }
    
    @SuppressWarnings("synthetic-access")
    public static <T> Maybe<T> just(final T t) {
        Validate.isNotNull("t", t);
        return new Just<T>() {
            @Override
            public T just() {
                return t;
            }
        };
    }
    
    public boolean isJust() {
        return (this instanceof Just<?>);
    }

    @SuppressWarnings("synthetic-access")
    public static <T> Maybe<T> nothing() {
        return new Nothing<T>() {
            // nothing required
        };
    }
    
    public boolean isNothing() {
        return (this instanceof Nothing<?>);
    }
}
