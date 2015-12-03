/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BrightspaceManifest;

import DiscussionBoard.DiscussionBoard;
import Group.Group;
import java.io.File;
import java.util.ArrayList;
import java.util.Random;
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
public class Gradebook {

    private ArrayList<Group> groups;
    private String savePath;

    public Gradebook() {
        groups = new ArrayList<>();
    }

    public void createGradebook() {
        try {

            // Standard DOM procedures
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder;

            docBuilder = docFactory.newDocumentBuilder();

            // root elements
            Document doc = docBuilder.newDocument();

            Element rootElement = doc.createElement("grades");
            doc.appendChild(rootElement);

            Element configuration = doc.createElement("configuration");
            rootElement.appendChild(configuration);

            Element gItems = doc.createElement("items");

            rootElement.appendChild(gItems);

            Element autoUpdate = doc.createElement("auto_update_final_grade");
            Element gradeSystem = doc.createElement("grading_system");
            Element emptyGradesFin = doc.createElement("include_empty_grades_in_final");
            Element defScheme = doc.createElement("default_scheme");
            Element userName = doc.createElement("show_user_name");
            Element userEmail = doc.createElement("show_user_email");

            autoUpdate.appendChild(doc.createTextNode("1"));
            gradeSystem.appendChild(doc.createTextNode("1"));
            emptyGradesFin.appendChild(doc.createTextNode("false"));
            defScheme.appendChild(doc.createTextNode("381"));
            userName.appendChild(doc.createTextNode("0"));
            userEmail.appendChild(doc.createTextNode("0"));

            configuration.appendChild(autoUpdate);
            configuration.appendChild(gradeSystem);
            configuration.appendChild(emptyGradesFin);
            configuration.appendChild(defScheme);
            configuration.appendChild(userName);
            configuration.appendChild(userEmail);

            
            Random randomGen = new Random();
            
            
            int id = 1;
            int identifier = randomGen.nextInt(400001) + 100000;
            for (Group group : groups) {
                for (DiscussionBoard discussionBoard : group.getDiscussionBoards()) {
                    discussionBoard.setGradeItemIdent(id);
                    discussionBoard.setGradeResourceCode(identifier);
                    int test = Integer.valueOf(group.getNumGroups());
                    for (int i = 0; i < test; i++) {
                        Element gItem = doc.createElement("item");
                        gItems.appendChild(gItem);

                        gItem.setAttribute("id", Integer.toString(id));
                        gItem.setAttribute("identifier", Integer.toString(id));
                        gItem.setAttribute("resource_code", "byui_produ-" + Integer.toString(identifier));

                        Element category_id = doc.createElement("category_id");
                        Element name = doc.createElement("name");
                        Element short_name = doc.createElement("short_name");
                        Element sort_order = doc.createElement("sort_order");
                        Element show_average = doc.createElement("show_average");
                        Element show_distribution = doc.createElement("show_distribution");
                        Element description = doc.createElement("description");
                        Element type_id = doc.createElement("type_id");
                        Element is_active = doc.createElement("is_active");
                        Element scoring = doc.createElement("scoring");
                        Element canExceed = doc.createElement("can_exceed_weight");
                        Element out_of = doc.createElement("out_of");
                        Element isBonus = doc.createElement("is_bonus");
                        Element max_grade = doc.createElement("max_grade");
                        Element exclude = doc.createElement("exclude_from_final_grade_calc");

                        name.setTextContent(discussionBoard.getName() + " (Group # " + (i + 1) + ")");
                        sort_order.setTextContent(Integer.toString(id));

                        show_average.setTextContent("false");
                        show_distribution.setTextContent("false");
                        description.setAttribute("text_type", "text/html");
                        description.setAttribute("is_displayed", "false");
                        type_id.setTextContent("1");
                        is_active.setTextContent("true");
                        canExceed.setTextContent("false");
                        out_of.setTextContent(discussionBoard.getPointValue());
                        isBonus.setTextContent("false");
                        max_grade.setTextContent(discussionBoard.getPointValue());
                        exclude.setTextContent("false");

                        gItem.appendChild(category_id);
                        gItem.appendChild(name);
                        gItem.appendChild(short_name);
                        gItem.appendChild(sort_order);
                        gItem.appendChild(show_average);
                        gItem.appendChild(show_distribution);
                        gItem.appendChild(description);
                        gItem.appendChild(type_id);
                        gItem.appendChild(is_active);
                        gItem.appendChild(scoring);

                        scoring.appendChild(canExceed);
                        scoring.appendChild(out_of);
                        scoring.appendChild(isBonus);
                        scoring.appendChild(max_grade);
                        scoring.appendChild(exclude);

                        identifier++;
                        id++;
                    }
                }
            }

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File((savePath) + "\\grades_d2l.xml"));

            // Output to console for testing
            transformer.transform(source, result);

            System.out.println("Gradebook Saved!");

        } catch (ParserConfigurationException ex) {
        } catch (TransformerConfigurationException ex) {
        } catch (TransformerException ex) {
        }

    }

    public ArrayList<Group> getGroups() {
        return groups;
    }

    public void setGroups(ArrayList<Group> groups) {
        this.groups = groups;
    }

    public String getSavePath() {
        return savePath;
    }

    public void setSavePath(String savePath) {
        this.savePath = savePath;
    }

}
