# html2pdfua

A java based CLI tool to create accessible PDF documents from HTML.

This repository is a simple wrapper around the [danfickle/openhtmltopdf](https://github.com/danfickle/openhtmltopdf).

Since [danfickle/openhtmltopdf](https://github.com/danfickle/openhtmltopdf) is a java lib,
and not all of us developers like to deal with java, The purpose of this repository is to easily 
create accessible PDF from the command line.

Using the jar file, you can trigger the generation of accessible PDFs from the command line.
So you should be able to use this tool from any programming language.

## Requirements
Make sure that Java is installed on your system and can be executed from the command line. For example `java -version`

## Get started
1. Download `./target/html2pdfua-{version}.jar`
2. Execute `java -jar html2pdfua-{version}.jar "path/to/your.html > path/to/output.pdf"`

### Create multiple pdfs in one go
Append multiple mappings in quotes as arguments. Example
```
java -jar html2pdfua.jar "path/to/your-1.html > path/to/output-1.pdf" "path/to/your-2.html > path/to/output-2.pdf" ...
```
## Guidelines
+ Use the template from the examples.
+ Use a clean xml syntax. For example `<br/>` instead of `<br>`
+ At least one font must be included
+ References to CSS and images relative to the file they are referenced from. Look at the example.

## Guidelines from [Open Html to Pdf wiki page](https://github.com/danfickle/openhtmltopdf/wiki/PDF-Accessibility-(PDF-UA,-WCAG,-Section-508)-Support)
+ Provide a `lang` attribute on the `html` element with the document language as an ISO code.
+ Provide a proper title in the `title` element.
+ Provide a meta description, subject and author.
+ Provide bookmarks linking to the sections of your document, especially for documents larger than a handful of pages.
+ Provide a page header or footer with page number information.
+ Content in page margins and fixed position elements is marked as pagination artefacts so avoid putting critical information in such places.
+ If using background colors or images, remember to use a good contrast for text over them.
+ Images and other replaced elements like SVG are required to have an `alt` attribute with alternate text.
+ Links are required to have a `title` attribute with a description of the link target.
+ PDF/UA prohibits the use of built-in fonts so make sure to provide a font and specify it for the `body` element and any page margin rules (eg. `@top-center`).
+ Avoid using `overflow:hidden`. The PDF specification makes it very hard to include clips in tagged content and this implementation is not expected to be clip compatible.
+ Avoid using transforms in content (you can use them in running boxes such as the page margin and fixed position elements).
+ Avoid using `position` with `relative` or `absolute`. Out-of-flow content upsets the reading order. Again you can use them in running elements.
+ Use the `float` property with caution, it can upset the reading order of the document.
+ Avoid using `table` elements for layout. Instead, if desiring a table based layout, use a `div` with `display: table`, `display: table-row`, etc.
+ Avoid, if possible, images over two or more pages.
+ Nest header levels correctly (`h1`, `h2`, etc).
+ This implementation does not yet cover form controls, such as the `input` element.

## Documentation
Check out the [wiki page of openhtmltopdf](https://github.com/danfickle/openhtmltopdf/wiki/PDF-Accessibility-(PDF-UA,-WCAG,-Section-508)-Support) 
for details of an accessible PDF.

Furthermore, you will find some examples and documentation in the wiki page on how to design the HTML pages to achieve certain features.

## Licence
Since the core of the jar package is [Open Html to PDF](https://github.com/danfickle/openhtmltopdf), 
the same license applies as for Open Html to PDF:

Html2PDFUA is distributed under the LGPL.
Html2PDFUA itself is licensed under the GNU Lesser General Public License, version 2.1 or later, available at 
[http://www.gnu.org/copyleft/lesser.html](http://www.gnu.org/copyleft/lesser.html).
You can use Html2PDFUA in any way and for any purpose you want as long as you respect the terms of the license.

## Credits
Html2PDFUA is based in [Open HTML to PDF](https://github.com/danfickle/openhtmltopdf).
Credit goes to the contributors of that project.