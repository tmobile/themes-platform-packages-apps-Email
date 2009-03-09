/*
 * Copyright (C) 2008 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.android.email.mail.internet;

import com.android.email.mail.MessagingException;

import android.test.suitebuilder.annotation.SmallTest;

import junit.framework.TestCase;

/**
 * This is a series of unit tests for the MimeUtility class.  These tests must be locally
 * complete - no server(s) required.
 */
@SmallTest
public class MimeUtilityTest extends TestCase {

    // TODO:  tests for unfold(String s)
    // TODO:  tests for decode(String s)
    // TODO:  tests for unfoldAndDecode(String s)
    // TODO:  tests for foldAndEncode(String s)
    // TODO:  tests for getHeaderParameter(String header, String name)
    // TODO:  tests for findFirstPartByMimeType(Part part, String mimeType)
    // TODO:  tests for findPartByContentId(Part part, String contentId) throws Exception
    
    /** Tests for getTextFromPart(Part part) */
    public void testGetTextFromPartContentTypeCase() throws MessagingException {
        final String theText = "This is the text of the part";
        TextBody tb = new TextBody(theText);
        MimeBodyPart p = new MimeBodyPart();
        
        // 1. test basic text/plain mode
        p.setHeader(MimeHeader.HEADER_CONTENT_TYPE, "text/plain");
        p.setBody(tb);
        String gotText = MimeUtility.getTextFromPart(p);
        assertEquals(theText, gotText);
        
        // 2. mixed case is OK
        p.setHeader(MimeHeader.HEADER_CONTENT_TYPE, "TEXT/PLAIN");
        p.setBody(tb);
        gotText = MimeUtility.getTextFromPart(p);
        assertEquals(theText, gotText);
        
        // 3. wildcards OK
        p.setHeader(MimeHeader.HEADER_CONTENT_TYPE, "text/other");
        p.setBody(tb);
        gotText = MimeUtility.getTextFromPart(p);
        assertEquals(theText, gotText);
    }
    // TODO: Tests of charset decoding in getTextFromPart()
    
    /** Tests for various aspects of mimeTypeMatches(String mimeType, String matchAgainst) */
    public void testMimeTypeMatches() {
        // 1. No match
        assertFalse(MimeUtility.mimeTypeMatches("foo/bar", "TEXT/PLAIN"));
        
        // 2. Match
        assertTrue(MimeUtility.mimeTypeMatches("text/plain", "text/plain"));
        
        // 3. Match (mixed case)
        assertTrue(MimeUtility.mimeTypeMatches("text/plain", "TEXT/PLAIN"));
        assertTrue(MimeUtility.mimeTypeMatches("TEXT/PLAIN", "text/plain"));
        
        // 4. Match (wildcards)
        assertTrue(MimeUtility.mimeTypeMatches("text/plain", "*/plain"));
        assertTrue(MimeUtility.mimeTypeMatches("text/plain", "text/*"));
        assertTrue(MimeUtility.mimeTypeMatches("text/plain", "*/*"));
        
        // 5. No Match (wildcards)
        assertFalse(MimeUtility.mimeTypeMatches("foo/bar", "*/plain"));
        assertFalse(MimeUtility.mimeTypeMatches("foo/bar", "text/*"));
    }
    
    /** Tests for various aspects of mimeTypeMatches(String mimeType, String[] matchAgainst) */
    public void testMimeTypeMatchesArray() {
        // 1. Zero-length array
        String[] arrayZero = new String[0];
        assertFalse(MimeUtility.mimeTypeMatches("text/plain", arrayZero));
        
        // 2. Single entry, no match
        String[] arrayOne = new String[] { "text/plain" };
        assertFalse(MimeUtility.mimeTypeMatches("foo/bar", arrayOne));
        
        // 3. Single entry, match
        assertTrue(MimeUtility.mimeTypeMatches("text/plain", arrayOne));
        
        // 4. Multi entry, no match
        String[] arrayTwo = new String[] { "text/plain", "match/this" };
        assertFalse(MimeUtility.mimeTypeMatches("foo/bar", arrayTwo));
        
        // 5. Multi entry, match first
        assertTrue(MimeUtility.mimeTypeMatches("text/plain", arrayTwo));
        
        // 6. Multi entry, match not first
        assertTrue(MimeUtility.mimeTypeMatches("match/this", arrayTwo));
    }

    // TODO:  tests for decodeBody(InputStream in, String contentTransferEncoding)    
    // TODO:  tests for collectParts(Part part, ArrayList<Part> viewables, ArrayList<Part> attachments)
}