package cf.gui;

/*
2    * Copyright (c) 2002 Sun Microsystems, Inc. All  Rights Reserved.
3    *
4    * Redistribution and use in source and binary forms, with or without
5    * modification, are permitted provided that the following conditions
6    * are met:
7    *
8    * -Redistributions of source code must retain the above copyright
9    *  notice, this list of conditions and the following disclaimer.
10   *
11   * -Redistribution in binary form must reproduct the above copyright
12   *  notice, this list of conditions and the following disclaimer in
13   *  the documentation and/or other materials provided with the distribution.
14   *
15   * Neither the name of Sun Microsystems, Inc. or the names of contributors
16   * may be used to endorse or promote products derived from this software
17   * without specific prior written permission.
18   *
19   * This software is provided "AS IS," without a warranty of any kind. ALL
20   * EXPRESS OR IMPLIED CONDITIONS, REPRESENTATIONS AND WARRANTIES, INCLUDING
21   * ANY IMPLIED WARRANTY OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE
22   * OR NON-INFRINGEMENT, ARE HEREBY EXCLUDED. SUN AND ITS LICENSORS SHALL NOT
23   * BE LIABLE FOR ANY DAMAGES OR LIABILITIES SUFFERED BY LICENSEE AS A RESULT
24   * OF OR RELATING TO USE, MODIFICATION OR DISTRIBUTION OF THE SOFTWARE OR ITS
25   * DERIVATIVES. IN NO EVENT WILL SUN OR ITS LICENSORS BE LIABLE FOR ANY LOST
26   * REVENUE, PROFIT OR DATA, OR FOR DIRECT, INDIRECT, SPECIAL, CONSEQUENTIAL,
27   * INCIDENTAL OR PUNITIVE DAMAGES, HOWEVER CAUSED AND REGARDLESS OF THE THEORY
28   * OF LIABILITY, ARISING OUT OF THE USE OF OR INABILITY TO USE SOFTWARE, EVEN
29   * IF SUN HAS BEEN ADVISED OF THE POSSIBILITY OF SUCH DAMAGES.
30   *
31   * You acknowledge that Software is not designed, licensed or intended for
32   * use in the design, construction, operation or maintenance of any nuclear
33   * facility.
34   */


 import java.io.File;
  import java.util.Hashtable;
  import java.util.Enumeration;
  import javax.swing.filechooser.*;

  /**
   * A convenience implementation of FileFilter that filters out
   * all files except for those type extensions that it knows about.
   *
   * Extensions are of the type ".foo", which is typically found on
   * Windows and Unix boxes, but not on Macinthosh. Case is ignored.
   *
   * Example - create a new filter that filerts out all files
   * but gif and jpg image files:
   *
   *     JFileChooser chooser = new JFileChooser();
   *     ExampleFileFilter filter = new ExampleFileFilter(
  *                   new String{"gif", "jpg"}, "JPEG & GIF Images")
   *     chooser.addChoosableFileFilter(filter);
   *     chooser.showOpenDialog(this);
   *
   * @version 1.5 06/13/02
   * @author Jeff Dinkins
   */
  public class ExampleFileFilter extends FileFilter {

      private static String TYPE_UNKNOWN = "Type Unknown";
      private static String HIDDEN_FILE = "Hidden File";

      private Hashtable filters = null;
      private String description = null;
      private String fullDescription = null;
      private boolean useExtensionsInDescription = true;

      /**
       * Creates a file filter. If no filters are added, then all
       * files are accepted.
       *
       * @see #addExtension
       */
      public ExampleFileFilter() {
      this.filters = new Hashtable();
      }

      /**
       * Creates a file filter that accepts files with the given extension.
       * Example: new ExampleFileFilter("jpg");
       *
       * @see #addExtension
       */
      public ExampleFileFilter(String extension) {
        this(extension,null);
      }

     /**
7       * Creates a file filter that accepts the given file type.
       * Example: new ExampleFileFilter("jpg", "JPEG Image Images");
       *
0      * Note that the "." before the extension is not needed. If
1      * provided, it will be ignored.
2      *
3      * @see #addExtension
4      */
     public ExampleFileFilter(String extension, String description) {
        this();
        if(extension!=null) addExtension(extension);
        if(description!=null) setDescription(description);
     }

     /**
      * Creates a file filter from the given string array.
      * Example: new ExampleFileFilter(String {"gif", "jpg"});
      *
      * Note that the "." before the extension is not needed adn
      * will be ignored.
      *
      * @see #addExtension
      */
     public ExampleFileFilter(String[] filters) {
    this(filters, null);
     }

     /**
      * Creates a file filter from the given string array and description.
      * Example: new ExampleFileFilter(String {"gif", "jpg"}, "Gif and JPG Images");
127      *
128      * Note that the "." before the extension is not needed and will be ignored.
129      *
130      * @see #addExtension
131      */
     public ExampleFileFilter(String[] filters, String description) {
     this();
     for (int i = 0; i < filters.length; i++) {
         // add filters one by one
         addExtension(filters[i]);
     }
     if(description!=null) setDescription(description);
     }

     /**
142      * Return true if this file should be shown in the directory pane,
143      * false if it shouldn't.
144      *
145      * Files that begin with "." are ignored.
146      *
147      * @see #getExtension
148      * @see FileFilter#accepts
149      */
     public boolean accept(File f) {
     if(f != null) {
         if(f.isDirectory()) {
         return true;
         }
         String extension = getExtension(f);
         if(extension != null && filters.get(getExtension(f)) != null) {
         return true;
         };
    }
     return false;
     }

     /**
      * Return the extension portion of the file's name .
165      *
166      * @see #getExtension
167      * @see FileFilter#accept
168      */
      public String getExtension(File f) {
     if(f != null) {
         String filename = f.getName();
         int i = filename.lastIndexOf('.');
         if(i>0 && i<filename.length()-1) {
         return filename.substring(i+1).toLowerCase();
         };
     }
     return null;
     }

     /**
181      * Adds a filetype "dot" extension to filter against.
182      *
183      * For example: the following code will create a filter that filters
184      * out all files except those that end in ".jpg" and ".tif":
185      *
186      *   ExampleFileFilter filter = new ExampleFileFilter();
187      *   filter.addExtension("jpg");
188      *   filter.addExtension("tif");
189      *
190      * Note that the "." before the extension is not needed and will be ignored.
191      */
     public void addExtension(String extension) {
     if(filters == null) {
         filters = new Hashtable(5);
     }
     filters.put(extension.toLowerCase(), this);
     fullDescription = null;
     }


   /**
      * Returns the human readable description of this filter. For
      * example: "JPEG and GIF Image Files (*.jpg, *.gif)"
      *
205      * @see setDescription
206      * @see setExtensionListInDescription
207      * @see isExtensionListInDescription
208      * @see FileFilter#getDescription
209      */
     public String getDescription() {
     if(fullDescription == null) {
         if(description == null || isExtensionListInDescription()) {
         fullDescription = description==null ? "(" : description + " (";
         // build the description from the extension list
         Enumeration extensions = filters.keys();
         if(extensions != null) {
             fullDescription += "." + (String) extensions.nextElement();
             while (extensions.hasMoreElements()) {
             fullDescription += ", ." + (String) extensions.nextElement();
             }
         }
         fullDescription += ")";
         } else {
         fullDescription = description;
         }
     }
     return fullDescription;
     }

     /**
231      * Sets the human readable description of this filter. For
232      * example: filter.setDescription("Gif and JPG Images");
233      *
234      * @see setDescription
235      * @see setExtensionListInDescription
236      * @see isExtensionListInDescription
237      */
     public void setDescription(String description) {
    this.description = description;
     fullDescription = null;
     }

     /**
      * Determines whether the extension list (.jpg, .gif, etc) should
      * show up in the human readable description.
      *
      * Only relevent if a description was provided in the constructor
      * or using setDescription();
      *
      * @see getDescription
      * @see setDescription
      * @see isExtensionListInDescription
      */
     public void setExtensionListInDescription(boolean b) {
     useExtensionsInDescription = b;
     fullDescription = null;
     }

     /**
0      * Returns whether the extension list (.jpg, .gif, etc) should
261      * show up in the human readable description.
262      *
263      * Only relevent if a description was provided in the constructor
264      * or using setDescription();
265      *
266      * @see getDescription
267      * @see setDescription
268      * @see setExtensionListInDescription
269      */
     public boolean isExtensionListInDescription() {
     return useExtensionsInDescription;
     }
 }
