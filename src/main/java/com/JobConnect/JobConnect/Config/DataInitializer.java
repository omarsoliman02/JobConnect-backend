package com.JobConnect.JobConnect.Config;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.JobConnect.JobConnect.Model.Applicant;
import com.JobConnect.JobConnect.Model.Application;
import com.JobConnect.JobConnect.Model.Company;
import com.JobConnect.JobConnect.Model.Experience;
import com.JobConnect.JobConnect.Model.Job;
import com.JobConnect.JobConnect.Model.Skill;
import com.JobConnect.JobConnect.Repository.ApplicantRepository;
import com.JobConnect.JobConnect.Repository.ApplicationRepository;
import com.JobConnect.JobConnect.Repository.CompanyRepository;
import com.JobConnect.JobConnect.Repository.ExperienceRepository;
import com.JobConnect.JobConnect.Repository.JobRepository;
import com.JobConnect.JobConnect.Repository.SkillRepository;

@Configuration
public class DataInitializer {

    @Autowired
    private CompanyRepository companyRepository;
    
    @Autowired
    private JobRepository jobRepository;
    
    @Autowired
    private ApplicantRepository applicantRepository;
    
    @Autowired
    private SkillRepository skillRepository;
    
    @Autowired
    private ExperienceRepository experienceRepository;
    
    @Autowired
    private ApplicationRepository applicationRepository;

    @Bean
    CommandLineRunner loadData() {
        return args -> {
            // Only initialize if the database is empty
            if (companyRepository.count() == 0) {
                System.out.println("Initializing database with mock data...");
                
                // 1. Create Companies
                List<Company> companies = createCompanies();
                
                // 2. Create Skills
                List<Skill> skills = createSkills();
                
                // 3. Create Applicants
                List<Applicant> applicants = createApplicants(skills);
                
                // 4. Create Jobs
                List<Job> jobs = createJobs(companies);
                
                // 5. Create Experiences
                createExperiences(applicants);
                
                // 6. Create Applications
                createApplications(applicants, jobs);
                
                System.out.println("Database initialization complete!");
            } else {
                System.out.println("Database already contains data, skipping initialization.");
            }
        };
    }
    
    private List<Company> createCompanies() {
        List<Company> companies = new ArrayList<>();
        
        companies.add(new Company("Acme Corp"));
        companies.add(new Company("Tech Solutions"));
        companies.add(new Company("EduSoft"));
        companies.add(new Company("HealthPlus"));
        companies.add(new Company("Green Energy Inc"));
        
        return companyRepository.saveAll(companies);
    }
    
    private List<Skill> createSkills() {
        List<Skill> skills = new ArrayList<>();
        
        skills.add(new Skill("Java"));
        skills.add(new Skill("Spring Boot"));
        skills.add(new Skill("Angular"));
        skills.add(new Skill("React"));
        skills.add(new Skill("SQL"));
        skills.add(new Skill("Python"));
        skills.add(new Skill("AWS"));
        skills.add(new Skill("Docker"));
        
        return skillRepository.saveAll(skills);
    }
    
    private List<Applicant> createApplicants(List<Skill> skills) {
        List<Applicant> applicants = new ArrayList<>();
        
        Applicant alice = new Applicant("Alice", "Smith", "alice@example.com", "0100000001");
        alice.addSkill(skills.get(0));  // Java
        alice.addSkill(skills.get(1));  // Spring Boot
        applicants.add(alice);
        
        Applicant bob = new Applicant("Bob", "Johnson", "bob@example.com", "0100000002");
        bob.addSkill(skills.get(2));    // Angular
        bob.addSkill(skills.get(3));    // React
        applicants.add(bob);
        
        Applicant carol = new Applicant("Carol", "Williams", "carol@example.com", "0100000003");
        carol.addSkill(skills.get(4));  // SQL
        carol.addSkill(skills.get(7));  // Docker
        applicants.add(carol);
        
        Applicant david = new Applicant("David", "Brown", "david@example.com", "0100000004");
        david.addSkill(skills.get(5));  // Python
        david.addSkill(skills.get(6));  // AWS
        applicants.add(david);
        
        Applicant eve = new Applicant("Eve", "Jones", "eve@example.com", "0100000005");
        eve.addSkill(skills.get(0));    // Java
        eve.addSkill(skills.get(5));    // Python
        applicants.add(eve);
        
        Applicant frank = new Applicant("Frank", "Miller", "frank@example.com", "0100000006");
        frank.addSkill(skills.get(1));  // Spring Boot
        frank.addSkill(skills.get(4));  // SQL
        applicants.add(frank);
        
        Applicant grace = new Applicant("Grace", "Davis", "grace@example.com", "0100000007");
        grace.addSkill(skills.get(2));  // Angular
        grace.addSkill(skills.get(6));  // AWS
        applicants.add(grace);
        
        Applicant hank = new Applicant("Hank", "Garcia", "hank@example.com", "0100000008");
        hank.addSkill(skills.get(3));   // React
        hank.addSkill(skills.get(7));   // Docker
        applicants.add(hank);
        
        Applicant ivy = new Applicant("Ivy", "Rodriguez", "ivy@example.com", "0100000009");
        ivy.addSkill(skills.get(0));    // Java
        ivy.addSkill(skills.get(4));    // SQL
        applicants.add(ivy);
        
        Applicant juliet = new Applicant("Juliet", "Stewart", "juliet@example.com", "0100000010");
        juliet.addSkill(skills.get(1)); // Spring Boot
        juliet.addSkill(skills.get(5)); // Python
        applicants.add(juliet);
        
        return applicantRepository.saveAll(applicants);
    }
    
    private List<Job> createJobs(List<Company> companies) {
        List<Job> jobs = new ArrayList<>();
        
        Job job1 = new Job("Backend Engineer", "Build REST APIs", "Paris");
        companies.get(0).addJob(job1);
        jobs.add(job1);
        
        Job job2 = new Job("Fullstack Developer", "Java + Angular features", "Lyon");
        companies.get(0).addJob(job2);
        jobs.add(job2);
        
        Job job3 = new Job("DevOps Engineer", "Manage CI/CD & Docker", "Marseille");
        companies.get(1).addJob(job3);
        jobs.add(job3);
        
        Job job4 = new Job("Frontend Developer", "React single-page apps", "Nice");
        companies.get(1).addJob(job4);
        jobs.add(job4);
        
        Job job5 = new Job("Data Analyst", "SQL queries & dashboards", "Bordeaux");
        companies.get(2).addJob(job5);
        jobs.add(job5);
        
        Job job6 = new Job("E-Learning Specialist", "Course content dev", "Toulouse");
        companies.get(2).addJob(job6);
        jobs.add(job6);
        
        Job job7 = new Job("QA Engineer", "Automated tests", "Lille");
        companies.get(3).addJob(job7);
        jobs.add(job7);
        
        Job job8 = new Job("Product Manager", "Lead health app roadmap", "Strasbourg");
        companies.get(3).addJob(job8);
        jobs.add(job8);
        
        Job job9 = new Job("Cloud Architect", "Design AWS infrastructure", "Rennes");
        companies.get(4).addJob(job9);
        jobs.add(job9);
        
        Job job10 = new Job("Sustainability Lead", "Green energy projects", "Nantes");
        companies.get(4).addJob(job10);
        jobs.add(job10);
        
        return jobRepository.saveAll(jobs);
    }
    
    private void createExperiences(List<Applicant> applicants) {
        List<Experience> experiences = new ArrayList<>();
        
        experiences.add(new Experience("Globex Inc", "Intern", 
                         LocalDate.of(2022, 6, 1), LocalDate.of(2022, 8, 31)));
        applicants.get(0).addExperience(experiences.get(0));
        
        experiences.add(new Experience("Initech", "Junior Dev", 
                         LocalDate.of(2023, 1, 1), LocalDate.of(2023, 12, 31)));
        applicants.get(1).addExperience(experiences.get(1));
        
        experiences.add(new Experience("Umbrella Corp", "Analyst", 
                         LocalDate.of(2021, 9, 1), LocalDate.of(2022, 5, 30)));
        applicants.get(2).addExperience(experiences.get(2));
        
        experiences.add(new Experience("Stark Labs", "Researcher", 
                         LocalDate.of(2020, 3, 1), LocalDate.of(2021, 2, 28)));
        applicants.get(3).addExperience(experiences.get(3));
        
        experiences.add(new Experience("Wayne Ent.", "Consultant", 
                         LocalDate.of(2022, 11, 1), LocalDate.of(2023, 4, 30)));
        applicants.get(4).addExperience(experiences.get(4));
        
        experiences.add(new Experience("Wonka Tech", "Engineer", 
                         LocalDate.of(2021, 7, 1), LocalDate.of(2022, 6, 30)));
        applicants.get(5).addExperience(experiences.get(5));
        
        experiences.add(new Experience("Cyberdyne", "Developer", 
                         LocalDate.of(2023, 2, 1), LocalDate.of(2024, 1, 31)));
        applicants.get(6).addExperience(experiences.get(6));
        
        experiences.add(new Experience("Soylent Corp", "Support", 
                         LocalDate.of(2020, 1, 1), LocalDate.of(2020, 12, 31)));
        applicants.get(7).addExperience(experiences.get(7));
        
        experiences.add(new Experience("Tyrell Corp", "Tester", 
                         LocalDate.of(2022, 4, 1), LocalDate.of(2023, 3, 31)));
        applicants.get(8).addExperience(experiences.get(8));
        
        experiences.add(new Experience("Vandelay Ind.", "Sales Rep", 
                         LocalDate.of(2021, 5, 1), LocalDate.of(2022, 4, 30)));
        applicants.get(9).addExperience(experiences.get(9));
        
        experienceRepository.saveAll(experiences);
    }
    
    private void createApplications(List<Applicant> applicants, List<Job> jobs) {
        List<Application> applications = new ArrayList<>();
        
        // Each applicant applies to 2 jobs
        applications.add(new Application(applicants.get(0), jobs.get(0)));
        applications.add(new Application(applicants.get(0), jobs.get(2)));
        
        applications.add(new Application(applicants.get(1), jobs.get(1)));
        applications.add(new Application(applicants.get(1), jobs.get(3)));
        
        applications.add(new Application(applicants.get(2), jobs.get(4)));
        applications.add(new Application(applicants.get(2), jobs.get(5)));
        
        applications.add(new Application(applicants.get(3), jobs.get(6)));
        applications.add(new Application(applicants.get(3), jobs.get(7)));
        
        applications.add(new Application(applicants.get(4), jobs.get(0)));
        applications.add(new Application(applicants.get(4), jobs.get(9)));
        
        applications.add(new Application(applicants.get(5), jobs.get(2)));
        applications.add(new Application(applicants.get(5), jobs.get(8)));
        
        applications.add(new Application(applicants.get(6), jobs.get(1)));
        applications.add(new Application(applicants.get(6), jobs.get(9)));
        
        applications.add(new Application(applicants.get(7), jobs.get(3)));
        applications.add(new Application(applicants.get(7), jobs.get(4)));
        
        applications.add(new Application(applicants.get(8), jobs.get(5)));
        applications.add(new Application(applicants.get(8), jobs.get(6)));
        
        applications.add(new Application(applicants.get(9), jobs.get(7)));
        applications.add(new Application(applicants.get(9), jobs.get(8)));
        
        applicationRepository.saveAll(applications);
    }
} 