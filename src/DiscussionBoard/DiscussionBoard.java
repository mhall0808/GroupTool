/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DiscussionBoard;

import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 *
 * @author hallm8
 */
public class DiscussionBoard {

    private String name;
    private String pointValue;
    private int gradeItemIdent;
    private int gradeResourceCode;

    private int boardIdent;
    private int boardID;
    private int groupSize;
    private int saveIdentity;

    private String savePath;

    public DiscussionBoard() {
        name = new String();
        pointValue = new String();
    }

    public DiscussionBoard(String name, String pointValue) {
        this.name = name;
        this.pointValue = pointValue;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPointValue() {
        return pointValue;
    }

    public void setPointValue(String pointValue) {
        this.pointValue = pointValue;
    }

    public String getDBoardText() {
        return "Name: " + name + " <" + pointValue + " points>";
    }

    public int getGradeItemIdent() {
        return gradeItemIdent;
    }

    public void setGradeItemIdent(int gradeItemIdent) {
        this.gradeItemIdent = gradeItemIdent;
    }

    public int getGradeResourceCode() {
        return gradeResourceCode;
    }

    public void setGradeResourceCode(int gradeResourceCode) {
        this.gradeResourceCode = gradeResourceCode;
    }

    public int getBoardIdent() {
        return boardIdent;
    }

    public void setBoardIdent(int boardIdent) {
        this.boardIdent = boardIdent;
    }

    public int getBoardID() {
        return boardID;
    }

    public void setBoardID(int boardID) {
        this.boardID = boardID;
    }

    public int getGroupSize() {
        return groupSize;
    }

    public void setGroupSize(int groupSize) {
        this.groupSize = groupSize;
    }

    public String getSavePath() {
        return savePath;
    }

    public void setSavePath(String savePath) {
        this.savePath = savePath;
    }

    public int getSaveIdentity() {
        return saveIdentity;
    }

    public void setSaveIdentity(int saveIdentity) {
        this.saveIdentity = saveIdentity;
    }

    
    
    public void buildDBoard() {
        try {
            // Standard DOM procedures
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder;

            docBuilder = docFactory.newDocumentBuilder();

            // root elements
            Document doc = docBuilder.newDocument();

            Element discussion = doc.createElement("discussion");
            Element forum = doc.createElement("forum");

            doc.appendChild(discussion);
            discussion.appendChild(forum);

            forum.setAttribute("id", Integer.toString(boardID));
            forum.setAttribute("resource_code", "byui_produ-" + boardIdent);

            // Since Brainhoney doesn't use Topics like I-Learn 3.0 does, we will always only have one topic.
            Element properties = doc.createElement("properties");
            Element content = doc.createElement("content");
            Element topicss = doc.createElement("topics");

            forum.appendChild(properties);
            forum.appendChild(content);
            forum.appendChild(topicss);

            Element allow_anon = doc.createElement("allow_anon");
            Element display_order = doc.createElement("display_order");
            Element is_hidden = doc.createElement("is_hidden");
            Element requires_approval = doc.createElement("requires_approval");
            Element is_locked = doc.createElement("is_locked");
            Element must_post = doc.createElement("must_post_to_participate");

            properties.appendChild(allow_anon);
            properties.appendChild(display_order);
            properties.appendChild(is_hidden);
            properties.appendChild(requires_approval);
            properties.appendChild(is_locked);
            properties.appendChild(must_post);

            allow_anon.setTextContent("False");
            display_order.setTextContent(Integer.toString(boardID));
            is_hidden.setTextContent("False");
            requires_approval.setTextContent("False");
            is_locked.setTextContent("False");
            must_post.setTextContent("False");

            Element title = doc.createElement("title");
            content.appendChild(title);
            title.setTextContent(name);

            saveIdentity = boardID;
            boardIdent++;
            boardID++;

            for (int i = 0; i < groupSize; i++) {
                Element topic = doc.createElement("topic");
                topicss.appendChild(topic);

                topic.setAttribute("id", Integer.toString(boardID));
                topic.setAttribute("resource_code", "byui_produ-" + Integer.toString(boardIdent));
                
                boardID++;
                boardIdent++;

                Element properties2 = doc.createElement("properties");
                Element allow_anon2 = doc.createElement("allow_anon");
                Element display_order2 = doc.createElement("display_order");
                Element is_hidden2 = doc.createElement("is_hidden");
                Element requires_approval2 = doc.createElement("requires_approval");
                Element is_locked2 = doc.createElement("is_locked");
                Element must_post2 = doc.createElement("must_post_to_participate");
                Element score_out_of = doc.createElement("score_out_of");

                Element gItem = doc.createElement("grade_item_id");
                Element nonInclude = doc.createElement("include_nonscored_values");

                properties2.appendChild(gItem);
                properties2.appendChild(nonInclude);
                properties2.appendChild(score_out_of);

                gItem.setTextContent("byui_produ-" + Integer.toString(gradeResourceCode));
                nonInclude.setTextContent("False");

                topic.appendChild(properties2);
                properties2.appendChild(allow_anon2);
                properties2.appendChild(display_order2);
                properties2.appendChild(is_hidden2);
                properties2.appendChild(requires_approval2);
                properties2.appendChild(is_locked2);
                properties2.appendChild(must_post2);

                allow_anon2.setTextContent("False");
                display_order2.setTextContent("1");
                is_hidden2.setTextContent("False");
                requires_approval2.setTextContent("False");
                is_locked2.setTextContent("False");
                must_post2.setTextContent("False");
                score_out_of.setTextContent(pointValue);

                Element content2 = doc.createElement("content");
                Element title2 = doc.createElement("title");
                topic.appendChild(content2);
                content2.appendChild(title2);
                title2.setTextContent(name + " # " + Integer.toString(i + 1));
                gradeItemIdent++;
                gradeResourceCode++;

            }

            // write the content into xml file
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);

            String pathAndName = savePath + "\\discussion_d2l_" + saveIdentity + ".xml";
            StreamResult result = new StreamResult(new File(pathAndName));

            // Output to console for testing
            transformer.transform(source, result);

            System.out.println("File saved!");

        } catch (ParserConfigurationException ex) {
            System.out.println("Error!!! Unable to save file! Something wrong!!");
        } catch (TransformerConfigurationException ex) {
        } catch (TransformerException ex) {
        }

    }

    public String getManifestLink() {

        return "<resource identifier=\"res_discuss_" + saveIdentity + "\" type=\"webcontent\" d2l_2p0:material_type=\"d2ldiscussion\" d2l_2p0:link_target=\"\" href=\"discussion_d2l_" + saveIdentity + ".xml\" title=\"\"/>";
    }

}
