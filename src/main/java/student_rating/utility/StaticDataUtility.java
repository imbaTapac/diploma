package student_rating.utility;

import student_rating.entity.Faculty;
import student_rating.entity.Group;
import student_rating.entity.OKR;
import student_rating.entity.Specialty;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Тарас on 17.04.2018.
 */
public class StaticDataUtility {
   private Group group;
   private Faculty faculty;
   private OKR okr;
   private Specialty specialty;

   private List<Group> groupList = new ArrayList();
   private List<Faculty>facultyList = new ArrayList();
   private List<OKR> okrList = new ArrayList();
   private List<Specialty> specialtyList = new ArrayList();

   public List<Specialty> getSpecialtyList() {
      return specialtyList;
   }

   public void setSpecialtyList(List<Specialty> specialtyList) {
      this.specialtyList = specialtyList;
   }

   public List<OKR> getOkrList() {
      return okrList;
   }

   public void setOkrList(List<OKR> okrList) {
      this.okrList = okrList;
   }

   public List<Faculty> getFacultyList() {
      return facultyList;
   }

   public void setFacultyList(List<Faculty> facultyList) {
      this.facultyList = facultyList;
   }

   public List<Group> getGroupList() {
      return groupList;
   }

   public void setGroupList(List<Group> groupList) {
      this.groupList = groupList;
   }

   public Specialty getSpecialty() {
      return specialty;
   }

   public void setSpecialty(Specialty specialty) {
      this.specialty = specialty;
   }

   public OKR getOkr() {
      return okr;
   }

   public void setOkr(OKR okr) {
      this.okr = okr;
   }

   public Faculty getFaculty() {
      return faculty;
   }

   public void setFaculty(Faculty faculty) {
      this.faculty = faculty;
   }

   public Group getGroup() {
      return group;
   }

   public void setGroup(Group group) {
      this.group = group;
   }

   void initialize(){
      okr = new OKR();
      okr.setName("Бакалавр");
      okrList.add(okr);
      okr = new OKR();
      okr.setName("Магістр");

      faculty = new Faculty();
      faculty.setName("ІТ");
   }
}
