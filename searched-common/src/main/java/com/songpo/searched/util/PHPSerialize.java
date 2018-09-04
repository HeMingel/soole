package com.songpo.searched.util;

import java.util.*;

public class PHPSerialize {
    /**
     * To store partially decomposed serialized string
     */
    private StringBuffer sb;

    /** Creates a new instance of phpserialize */
    public PHPSerialize() {
    }

    /**
     * Unserializes a single value into a PHPValue
     *
     *     s String to unserialize
     * @return PHPValue
     */
    public PHPValue unserialize(String s) {
        sb = new StringBuffer(s);
        PHPValue phpvalue = getNext();
        // println("value = " + phpvalue.toString());
        return phpvalue;
    }

    /**
     * Get the next token in the serialized string
     *
     *
     */
    private PHPValue getNext() {
        // println("getNext = "+ sb.toString());
        PHPValue phpvalue;
        Object value = new Object();
        // Current character
        String c;
        // What type of value is this
        String what = "";
        // Consume & throw away characters
        int consumeChars = 0;
        // Length of the string/array
        int len = -1;
        // Buffer to read in string
        StringBuffer tmp = new StringBuffer();
        // length of sb is constantly changing.
        while (sb.length() > 0) {
            c = sb.substring(0, 1);
            sb = sb.deleteCharAt(0);
            // println(c +"\twhat="+what+"\ttmp="+tmp.toString());
            // Ignore chars
            if (consumeChars-- > 0) {
                continue;
            }
            /**
             * This is a hackish pseudo state machine 'what' is the state
             *
             */
            if (what.equals("array_length")) {
                if (c.equals(":")) {
                    len = Integer.parseInt(tmp.toString());
                    tmp.delete(0, tmp.length());
                    // println("\tarray_length="+ len);
                    what = "array_value";
                }
                if (len == -1) {
                    tmp.append(c);
                }
            } else if (what.equals("array_value")) {
                // value = new PHPValue( new HashMap() );
                HashMap hash = new HashMap();
                for (int i = 0; i < len; i++) {
                    PHPValue hashKey = getNext();
                    PHPValue hashValue = getNext();
                    // Force the keys of the HashMap to be type String
                    hash.put(hashKey.value, hashValue);
                }
                value = hash;
                // return new PHPValue(value);
                what = "set";
            } else if (what.equals("string_length")) {
                if (c.equals(":")) {
                    len = Integer.parseInt(tmp.toString());
                    tmp.delete(0, tmp.length());
                    // println("\tstring_length="+ len);
                    what = "string_value";
                    consumeChars = 1;
                }
                if (len == -1) {
                    tmp.append(c);
                }
            } else if (what.equals("string_value")) {
                if (len-- > 0) {
                    tmp.append(c);
                } else {
                    value = tmp.toString();
                    what = "set";
                    // println("\tstring_value="+ value);
                }
            } else if (what.equals("integer")) {
                if (c.equals(";")) {
                    value = tmp.toString();
                    what = "set";
                } else {
                    tmp.append(c);
                }
            } else if (what.equals("double")) {
                if (c.equals(";")) {
                    value = tmp.toString();
                    what = "set";
                } else {
                    tmp.append(c);
                }
            } else if (what.equals("null")) {
                if (c.equals(";")) {
                    value = null;
                    what = "set";
                } else {
                    // Bad
                }
            }
            // What kind of variable is coming up next
            else {
                if (c.equals("a")) {
                    what = "array_length";
                } else if (c.equals("s")) {
                    what = "string_length";
                } else if (c.equals("i")) {
                    what = "integer";
                } else if (c.equals("d")) {
                    what = "double";
                } else if (c.equals("n")) {
                    what = "null";
                    // do NOT consume the next char ";"
                    continue;
                } else {
                    continue;
                }

                // Consume the ":" after variable type declaration
                consumeChars = 1;
                continue;
            }

            if (what.equals("set")) {
                return new PHPValue(value);
            }

        }

        return new PHPValue(value);
    }

}