/*
 * Copyright 2002 Sun Microsystems, Inc. All rights reserved.
 * SUN PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

//
//	Copyright (c) by 2001 Sun Microsystems, Inc.
//	All rights reserved.
//
//	Derived from "Folder-Tree":
//
//****************************************************************
// You are free to copy the "Folder-Tree" script as long as you
// keep this copyright notice:
// Script found in: http://www.geocities.com/Paris/LeftBank/2178/
// Author: Marcelino Alves Martins (martins@hks.com) December '97.
//****************************************************************

// Definition of class Folder
// *****************************************************************

function Folder(folderDescription, hreference, status_txt, icon, key) //constructor
{
  //constant data
  this.desc = "&nbsp;"+folderDescription+"&nbsp;"
  this.status_txt = status_txt
  this.hreference = hreference
  this.id = -1
  this.navObj = 0
  this.nodeImg = 0
  this.isLastNode = 0
  this.key = key;
  this.icon = "<img width=23 height=16 border=0 src=\""+icon+"\" alt=\"\">";

  //dynamic data
  this.isOpen = true
  this.children = new Array
  this.nChildren = 0

  //methods
  this.initialize = initializeFolder
  this.setState = setStateFolder
  this.addChild = addChild
  this.createIndex = createEntryIndex
  this.hide = hideFolder
  this.display = display
  this.renderOb = drawFolder
  this.subEntries = folderSubEntries
  this.outputLink = outputFolderLink
  this.doSelection = doFolderSelection
}

function setStateFolder(isOpen) {
  var subEntries
  var totalHeight
  var fIt = 0
  var i=0

  if (isOpen == this.isOpen) {
    return
  }

  if (is_nav4up) {
    totalHeight = 0
    for (i=0; i < this.nChildren; i++) {
      totalHeight = totalHeight + this.children[i].navObj.clip.height
    }
    subEntries = this.subEntries()
    if (this.isOpen)
      totalHeight = 0 - totalHeight
    for (fIt = this.id + subEntries + 1; fIt < nEntries; fIt++) {
	indexOfEntries[fIt].navObj.moveBy(0, totalHeight)
    }
  } // N4+

  this.isOpen = isOpen
  propagateChangesInState(this)
} // setStateFolder

function propagateChangesInState(folder) {
  var i=0
  var basepath = location.href.substring(0,
      location.href.lastIndexOf('/'));
  if (folder.isOpen) {
    if (folder.nodeImg) { // is node, now decide last or middle node image
      if (folder.isLastNode) {
	 folder.nodeImg.src = basepath+"/images/jmenu/handledownlast.gif"
      } else { // !lastNode
	 folder.nodeImg.src = basepath+"/images/jmenu/handledownmiddle.gif"
      }
    }
    for (i=0; i<folder.nChildren; i++) {
      folder.children[i].display()
    }
  } else { // folder !open
    if (folder.nodeImg) {
      if (folder.isLastNode) {
	 folder.nodeImg.src = basepath+"/images/jmenu/handlerightlast.gif"
      } else {
	folder.nodeImg.src = basepath+"/images/jmenu/handlerightmiddle.gif"
      }
    }
    for (i=0; i<folder.nChildren; i++) {
      folder.children[i].hide()
    }
  }
} // propagateChangesInState

function hideFolder() {
  if (is_nav4up) {
    this.navObj.visibility = "hidden"
  } else if (is_ie4up) {
    this.navObj.style.display = "none"
  }

  this.setState(0)
} // hideFolder

function initializeFolder(level, lastNode, leftSide) {

var j=0
var i=0
var numberOfFolders
var numberOfDocs
var nc
  nc = this.nChildren

  this.createIndex()

  var auxEv = "<a onMouseOver=\"window.status='" + this.status_txt + "'; return true\" href='javascript:openOrClose("+this.id+")'>"

  if (level>0) {
    if (lastNode) { // the last 'brother' in the children array
      this.renderOb(leftSide + auxEv + "<img name='nodeIcon" + this.id + "' id='nodeIcon" + this.id + "' src='images/jmenu/handledownlast.gif' width=16 height=22 border=0 alt='Open/Close'></a>")
      leftSide = leftSide + "<img src='images/jmenu/transparent.gif' width=16 height=22 alt=\"\">"
      this.isLastNode = 1
    } else {
      this.renderOb(leftSide + auxEv + "<img name='nodeIcon" + this.id + "' id='nodeIcon" + this.id + "' src='images/jmenu/handledownmiddle.gif' width=16 height=22 border=0 alt='Open/Close'></a>")
      leftSide = leftSide + "<img src='images/jmenu/linevertical.gif' width=16 height=22 alt=\"\">"
      this.isLastNode = 0
    }
  } else { // level == 0
    this.renderOb("")
}

  if (nc > 0) {
    level = level + 1
    for (i=0 ; i < this.nChildren; i++)  {
      if (i == this.nChildren-1) {
        this.children[i].initialize(level, 1, leftSide)
      } else {
        this.children[i].initialize(level, 0, leftSide)
      }
    } // for kids
  } // nc > 0
} // initializeFolder

function drawFolder(leftSide) {

  if (is_nav4up) {
    if (!doc.yPos) {
      doc.yPos=8
      }
    doc.write("<layer id='folder" + this.id + "' top=" + doc.yPos + " visibility=hidden>")
  } //n4
  doc.writeln("<table ")

  if (is_ie4up) {
    doc.write(" id='folder" + this.id + "' style='position:block;' ")
  }

  doc.writeln(" border=0 cellspacing=0 cellpadding=0>")
  doc.writeln("<tr><td bgcolor=" + BGCOLOR + ">")
  if (leftSide != "") {
    doc.write(leftSide)
  } else {
    doc.write("<img src='images/jmenu/transparent.gif' width=1 height=22 alt=\"\">");
  }
  // </TD MUST NOT BE PRECEEDED BY NEWLINE!!
  doc.write("</td><td valign=middle nowrap bgcolor=" + BGCOLOR + ">")

  this.outputLink();
  doc.write(this.icon);
  doc.write("</a>");
  doc.write("</td><td valign=middle nowrap id='hilightFolder" + this.id + "'>")
  this.outputLink()
  doc.write(this.desc + "</a>")

  // </TD MUST NOT BE PRECEEDED BY NEWLINE!!
  doc.write("</td></tr>")
  doc.write("</table>")

  if (is_nav4up) {
    doc.write("</layer>")
  }

  if (is_nav4up) {
    this.navObj = doc.layers["folder"+this.id]
    this.nodeImg = this.navObj.document.images["nodeIcon"+this.id]
    doc.yPos=doc.yPos+this.navObj.clip.height
  } else if (is_ie4up) {
    this.navObj = doc.getElementById("folder"+this.id)
    this.nodeImg = doc.getElementById("nodeIcon"+this.id)
  }

} // drawFolder

function doFolderSelection(entry) {
	 var key = ""
	 if (is_ie4up) {
	    key = "hilightFolder" + entry
	 } else {
	   key = "folder" + entry
	 }
	 doSelectionHighlight(key)
} // doFolderSelection

function doItemSelection(entry) {
	 var key = ""
	 if (is_ie4up) {
	    key = "hilightItem" + entry
	 } else {
	   key = "item" + entry
	 }
	 doSelectionHighlight(key)
} // doItemSelection

function doSelectionHighlight(key) {
	 if (selectedObj != "-1") {
	    if (is_ie4up) {
	       selectedObj.backgroundColor = BGCOLOR
	       selectedObj.border = "none white 0px"
	    } else {
	      selectedObj.bgColor = BGCOLOR
	    }
	 } else {
	 }

	 if (is_nav4up) {
	   selectedObj = doc.layers[key].document.links[1]
	   selectedObj = doc.layers[key]
	   selectedObj.bgColor = SELECTEDCOLOR
	 } else if (is_ie4up) {
	   selectedObj = doc.getElementById(key).style
	   selectedObj.backgroundColor = SELECTEDCOLOR
	   selectedObj.border = "solid " + EDGECOLOR + " 1px"
	 }
} // doSelectionHighlight

function openOrCloseFolder(folderID, href) {
	 clickOnFolder(folderID)
	 doFolderSelection(folderID)
	 loadContentFrame(href)
} // openOrCloseFolder

function loadContentFrame(href) {
	 // See comments under outputFolderLink()
	 // window.open(href, 'content')
	 window.open(href, CONTENTFRAME)
} // loadContentFrame

function outputFolderLink() {
  if (this.hreference) {
    /*
     * hack for N6: don't use separate href & onclick because href seemingly takes
     * processing precedence and the images needed by the onclick call don't draw properly :(
     * So give priority to the tree expansion by deferring the content frame
     * loading, formerly done by the href, into a subsequent call
     *
      doc.write("<a href='" + this.hreference + "' target=" + CONTENTFRAME + " ")
      doc.write("onClick='javascript:openOrCloseFolder("+this.id+")' >")
      */
    doc.write("<a href='javascript:openOrCloseFolder(" + this.id + ",\"" + this.hreference + "\")' ")
    doc.write("onMouseOver=\"window.status='" + this.status_txt + "'; return true\" ")
    doc.write(">")
  } else {
    doc.write("<a>")
  }
} // outputFolderLink

function addChild(childNode)
{
  this.children[this.nChildren] = childNode
  this.nChildren++
  return childNode
}

function folderSubEntries()
{
  var i = 0
  var se = this.nChildren

  for (i=0; i < this.nChildren; i++){
    if (this.children[i].children) //is a folder
      se = se + this.children[i].subEntries()
  }

  return se
}


// Definition of class Item (a document or link inside a Folder)
// *************************************************************

function Item(itemDescription, itemLink, status_txt, icon, key) // Constructor
{
  // constant data
  this.desc = "&nbsp;"+itemDescription+"&nbsp;";
  this.status_txt = status_txt
  this.link = itemLink
  this.id = -1 //initialized in initalize()
  this.navObj = 0 //initialized in render()
  this.key = key;
  this.icon = "<img width=23 height=16 border=0 src=\""+icon+"\" alt=\"\">";

  // methods
  this.initialize = initializeItem
  this.createIndex = createEntryIndex
  this.hide = hideItem
  this.display = display
  this.renderOb = drawItem
  this.doSelection = doItemSelection
}

function hideItem() {
  if (is_nav4up) {
    this.navObj.visibility = "hidden"
  } else if (is_ie4up) {
    this.navObj.style.display = "none"
  }
} // hideItem

function initializeItem(level, lastNode, leftSide) {
  this.createIndex()

  if (level>0)
    if (lastNode)  { // the last 'brother' in the children array
      this.renderOb(leftSide + "<img src='images/jmenu/linelastnode.gif' width=16 height=22 alt=\"\">")
    } else {
      this.renderOb(leftSide + "<img src='images/jmenu/linemiddlenode.gif' width=16 height=22 alt=\"\">")
    } else this.renderOb("")
} // initializeItem

function drawItem(leftSide) {
  if (is_nav4up) {
    doc.write("<layer id='item" + this.id + "' top=" + doc.yPos + " visibility=hidden>")
  }

  doc.writeln("<table ")
  if (is_ie4up) {
    doc.write(" id='item" + this.id + "' style='position:block;' ")
  }
  doc.writeln(" border=0 cellspacing=0 cellpadding=0>")
  doc.writeln("<tr><td bgcolor=" + BGCOLOR + ">")

  doc.write(leftSide)
  // </TD MUST NOT BE PRECEEDED BY NEWLINE!!
  doc.write("</td><td valign=middle nowrap bgcolor=" + BGCOLOR + ">")

  doc.write("<a href=" + this.link + " ")
  doc.write("onClick='javascript:doItemSelection("+this.id+")' ")
  doc.write("onMouseOver=\"window.status='" + this.status_txt + "'; return true\" ")
  doc.write(">")
  doc.write(this.icon)
  doc.write("</a>")
  doc.write("</td><td valign=middle nowrap");
  doc.write(" id='hilightItem"+this.id+"'>");
  doc.write("<a ");
  doc.write("href=" + this.link + " ")
  doc.write("onClick='javascript:doItemSelection("+this.id+")' ")
  doc.write("onMouseOver=\"window.status='" + this.status_txt + "'; return true\" ")
  doc.write(">")
  doc.write(this.desc)
  doc.write("</a>")

  // </TD MUST NOT BE PRECEEDED BY NEWLINE!!
  doc.write("</td></tr>")
  doc.write("</table>")

  if (is_nav4up) {
    doc.write("</layer>")
  }

  if (is_nav4up) {
    this.navObj = doc.layers["item"+this.id]
    doc.yPos=doc.yPos+this.navObj.clip.height
  } else if (is_ie4up) {
    this.navObj = doc.getElementById("item"+this.id)
  }
} // drawItem

// Methods common to both objects (pseudo-inheritance)
// ********************************************************

function display() {
  if (is_nav4up) {
    this.navObj.visibility = "show"
  } else if (is_ie4up) {
    this.navObj.style.display = ""
  }
} // display()

function createEntryIndex()
{
  this.id = nEntries
  indexOfEntries[nEntries] = this
  nEntries++
}

// Events
// *********************************************************

function clickOnFolder(folderId) {
  var clicked = indexOfEntries[folderId]

  // no-op if folder already open
  if (!clicked.isOpen) {
    openOrClose(folderId)
  }
  return
} // clickOnFolder

function openOrClose(folderId)
{
  var clickedFolder = 0
  var state = 0

  clickedFolder = indexOfEntries[folderId]
  state = clickedFolder.isOpen

  clickedFolder.setState(!state) //open<->close
  //doSelection(folderId)
  writeCookie();
}

function initializeDocument() {
   var cookie = document.cookie; // Get cookie before we change anything
   if (doc.yPos) {
      doc.yPos = 0	// required for resize under NNav4.
   }

   browserVersion() // requires browserVersion.js

  if (is_gecko) {
    // Use ie4 implementation for Netscape 6
    is_nav4up = 0;
    is_ie4up = 1;
  }

  foldersTree.initialize(0, 1, "")	// required for menu tree display under IE5
  foldersTree.display()

  if (is_nav4up) {
    doc.write("<layer top="+indexOfEntries[nEntries-1].navObj.top+">&nbsp;</layer>")
  }

    // close the whole tree
    openOrClose(0)
    // open the root folder
    openOrClose(0)
    doFolderSelection(0);

    // Netscape 6 has an image redrawing bug, so we can't modify the
    // state of the turners here.
    if (!is_gecko) {
      if (cookie) {
	  doCookie(cookie);
	  writeCookie();
      }

      if (top && top.content && top.content.yokeKey) {
	  yokeTo(top.content.yokeKey);
      }
    }

} // initializeDocument()

// Auxiliary Functions for Folder-Treee backward compatibility
// *********************************************************

function gFld(description, hreference, status_txt, icon, key)
{
  folder = new Folder(description, hreference, status_txt, icon, key)
  return folder
}

function gLnk(target, description, linkData, status_txt, icon, key)
{
  fullLink = ""

  if (target==0)
  {
    fullLink = "'" + linkData + "' target=" + CONTENTFRAME
  }
  else
  {
    if (target==1)
       fullLink = "'http://" + linkData + "' target=_blank"
    else
       fullLink = "'http://" + linkData + "' target=" + CONTENTFRAME
  }

  linkItem = new Item(description, fullLink, status_txt, icon, key)
  return linkItem
}

function insFld(parentFolder, childFolder)
{
  return parentFolder.addChild(childFolder)
}

function insDoc(parentFolder, document)
{
  parentFolder.addChild(document)
}

// Yoke the tree to the specified key.  I.e. open and highlight that entry
function yokeTo(key)
{
  if (this.foldersTree) {
    yokeToWalker(this.foldersTree, key);
  } else {
    // Race condition
  }
}

// Walk the tree, yoking to key
function yokeToWalker(obj, key)
{
    if (obj.children) {
	// Folder
	var found = 0;
	if (obj.key == key) {
	      doFolderSelection(obj.id);
	    found = 1;
	}
	var i;
	for (i=0; i<obj.nChildren && !found; i++) {
	  found = yokeToWalker(obj.children[i], key);
	}
        if (found) {
	  clickOnFolder(obj.id);
	  return found;
        }
    } else {
	// Item
	if (obj.key == key) {
	    doItemSelection(obj.id);
	    return 1;
	}
    }
    return 0;
}

// Write the cookie with the folder open/close state
function writeCookie() {
    var str = writeCookieWalker(this.foldersTree);
    document.cookie = "tree="+str;
}

// Walk the tree, generating the open/close state cookie
function writeCookieWalker(obj)
{
    var str = "";
    if (obj.children) {
	// Folder
	if (obj.isOpen) {
	    str = str+obj.key+".";
	}
	var i;
	for (i=0; i<obj.nChildren; i++) {
	  str = str + writeCookieWalker(obj.children[i]);
	}
    } else {
	// Item
    }
    return str;
}

// Apply an open/close cookie to the tree
function doCookie(str) {
    if (str) {
	var pos = str.indexOf(";");
	if (pos > 0) {
	    str = str.substring(0, pos);
	}
	var prefix = "tree=";
	pos = str.indexOf(prefix);
	if (pos >= 0) {
	    str = str.substring(prefix.length);
	    var cookie_keys = str.split(".");
	    var keys = new Object();
	    for (var i in cookie_keys) {
		if (i != "") {
		    var key = cookie_keys[i];
		    keys[key] = 1;
		}
	    }
	    doCookieWalker(this.foldersTree, keys);
	}
    }
}

// Walk the tree, applying the open/close state
function doCookieWalker(obj, keys)
{
    var str = "";
    if (obj.children) {
	var k = obj.key;
	if (keys[k]) {
	    obj.setState(1);
	}
	var i;
	for (i=0; i<obj.nChildren; i++) {
	  str =  doCookieWalker(obj.children[i], keys);
	}
    } else {
	// Item
    }
    return str;
}


// Global variables
// ****************

indexOfEntries = new Array
nEntries = 0
doc = document
GROUPTAG = "DIV"
selectedFolder=0
selectedObjID = -1
selectedObj = -1

// browser id and version vars are defined in browserVersion.js

BGCOLOR = "white"
SELECTEDCOLOR = "#CCCCFF"
EDGECOLOR = "#9999CC"
//CONTENTFRAME="content"
CONTENTFRAME="mainFrame"

