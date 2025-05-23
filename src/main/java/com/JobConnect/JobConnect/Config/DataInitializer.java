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
import com.JobConnect.JobConnect.Model.ExperienceLevel;
import com.JobConnect.JobConnect.Model.Job;
import com.JobConnect.JobConnect.Model.JobType;
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
        
        // Programming Languages
        skills.add(new Skill("Java"));
        skills.add(new Skill("Python"));
        skills.add(new Skill("JavaScript"));
        skills.add(new Skill("TypeScript"));
        skills.add(new Skill("C#"));
        skills.add(new Skill("C++"));
        skills.add(new Skill("Ruby"));
        skills.add(new Skill("PHP"));
        skills.add(new Skill("Swift"));
        skills.add(new Skill("Go"));
        skills.add(new Skill("Kotlin"));
        skills.add(new Skill("Rust"));
        
        // Web Frameworks & Libraries
        skills.add(new Skill("Spring Boot"));
        skills.add(new Skill("Angular"));
        skills.add(new Skill("React"));
        skills.add(new Skill("Vue.js"));
        skills.add(new Skill("ASP.NET"));
        skills.add(new Skill("Django"));
        skills.add(new Skill("Flask"));
        skills.add(new Skill("Express"));
        skills.add(new Skill("Laravel"));
        skills.add(new Skill("Ruby on Rails"));
        
        // Database
        skills.add(new Skill("SQL"));
        skills.add(new Skill("MySQL"));
        skills.add(new Skill("PostgreSQL"));
        skills.add(new Skill("MongoDB"));
        skills.add(new Skill("Redis"));
        skills.add(new Skill("Oracle"));
        skills.add(new Skill("Elasticsearch"));
        
        // DevOps & Cloud
        skills.add(new Skill("AWS"));
        skills.add(new Skill("Azure"));
        skills.add(new Skill("GCP"));
        skills.add(new Skill("Docker"));
        skills.add(new Skill("Kubernetes"));
        skills.add(new Skill("Jenkins"));
        skills.add(new Skill("GitLab CI"));
        skills.add(new Skill("Terraform"));
        
        // Mobile
        skills.add(new Skill("Android"));
        skills.add(new Skill("iOS"));
        skills.add(new Skill("React Native"));
        skills.add(new Skill("Flutter"));
        
        // AI & Data Science
        skills.add(new Skill("Machine Learning"));
        skills.add(new Skill("TensorFlow"));
        skills.add(new Skill("PyTorch"));
        skills.add(new Skill("Data Analysis"));
        skills.add(new Skill("NLP"));
        
        // Other
        skills.add(new Skill("Git"));
        skills.add(new Skill("Agile"));
        skills.add(new Skill("Scrum"));
        skills.add(new Skill("UI/UX"));
        
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
        
        // CreatedAt will be set automatically via @CreationTimestamp
        // Jobs will have created dates varying slightly to demonstrate sorting
        
        Job job1 = new Job("Backend Engineer", 
                "We are seeking a talented Backend Engineer to join our innovative development team. You will be responsible for developing and maintaining robust server-side applications, designing RESTful APIs, and working with databases to support our growing platform. The ideal candidate has strong experience with Java and Spring Boot, understands microservices architecture, and is passionate about writing clean, maintainable code. You'll collaborate with cross-functional teams to implement new features and optimize existing systems for performance and scalability.", 
                "Paris",
                JobType.FULL_TIME, 55000, 75000, ExperienceLevel.INTERMEDIATE);
        companies.get(0).addJob(job1);
        jobs.add(job1);
        
        Job job2 = new Job("Fullstack Developer", 
                "Join our product team as a Fullstack Developer and help build cutting-edge web applications that serve thousands of users. In this role, you'll work on both frontend and backend technologies, implementing responsive UI components with Angular and developing robust backend services with Java. You should have a solid understanding of web development principles, experience with RESTful API design, and the ability to write efficient database queries. We value developers who can take ownership of features from concept to deployment and are committed to writing testable, maintainable code.", 
                "Lyon",
                JobType.FULL_TIME, 60000, 80000, ExperienceLevel.SENIOR);
        companies.get(0).addJob(job2);
        jobs.add(job2);
        
        Job job3 = new Job("DevOps Engineer", 
                "We're looking for a skilled DevOps Engineer to help us build and maintain our cloud infrastructure and deployment pipelines. In this role, you'll be responsible for automating deployment processes, implementing CI/CD workflows, and managing container orchestration with Docker and Kubernetes. The ideal candidate has strong experience with AWS or other cloud platforms, understands infrastructure as code principles, and can troubleshoot complex system issues. Knowledge of monitoring tools and security best practices is a plus. You'll work closely with development teams to streamline workflows and improve system reliability.", 
                "Marseille",
                JobType.CONTRACT, 65000, 85000, ExperienceLevel.INTERMEDIATE);
        companies.get(1).addJob(job3);
        jobs.add(job3);
        
        Job job4 = new Job("Frontend Developer", 
                "We are seeking a creative Frontend Developer to build beautiful, responsive user interfaces for our web applications. As part of our UI team, you'll implement designs using React, HTML5, CSS3, and JavaScript, ensuring cross-browser compatibility and optimal performance. The ideal candidate has experience with modern frontend frameworks, understands UI/UX principles, and can write clean, maintainable code. You should be comfortable working in an agile environment and collaborating closely with designers and backend developers. Knowledge of state management, CSS preprocessors, and responsive design techniques is required.", 
                "Nice",
                JobType.PART_TIME, 45000, 60000, ExperienceLevel.ENTRY_LEVEL);
        companies.get(1).addJob(job4);
        jobs.add(job4);
        
        Job job5 = new Job("Data Analyst", 
                "Join our analytics team as a Data Analyst and help turn complex data into actionable insights. In this role, you'll design and create data reports, develop interactive dashboards, and perform ad-hoc analyses to support business decisions. You should have strong SQL skills, experience with data visualization tools, and the ability to communicate findings clearly to non-technical stakeholders. Knowledge of statistical methods and data modeling techniques is required. You'll work with cross-functional teams to understand their data needs and develop solutions that drive business growth and operational improvements.", 
                "Bordeaux",
                JobType.FULL_TIME, 50000, 70000, ExperienceLevel.INTERMEDIATE);
        companies.get(2).addJob(job5);
        jobs.add(job5);
        
        Job job6 = new Job("E-Learning Specialist", 
                "We're looking for an E-Learning Specialist to help develop engaging online educational content for our platform. In this role, you'll design interactive learning modules, create multimedia content, and collaborate with subject matter experts to develop comprehensive courses. The ideal candidate has experience with e-learning development tools, understands instructional design principles, and can adapt content for different learning styles. Knowledge of learning management systems and assessment techniques is required. You'll also analyze course metrics to continuously improve content effectiveness and user engagement.", 
                "Toulouse",
                JobType.CONTRACT, 40000, 55000, ExperienceLevel.ENTRY_LEVEL);
        companies.get(2).addJob(job6);
        jobs.add(job6);
        
        Job job7 = new Job("QA Engineer", 
                "We are seeking a detail-oriented QA Engineer to ensure the quality and reliability of our software products. In this role, you'll design and implement test plans, develop automated test frameworks, and perform manual testing when needed. You should have experience with testing methodologies, bug tracking systems, and automated testing tools. The ideal candidate understands the software development lifecycle, can identify edge cases, and communicates effectively with developers. You'll work closely with our development team to establish quality standards and implement processes that prevent defects from reaching production.", 
                "Lille",
                JobType.FULL_TIME, 45000, 65000, ExperienceLevel.INTERMEDIATE);
        companies.get(3).addJob(job7);
        jobs.add(job7);
        
        Job job8 = new Job("Product Manager", 
                "Join our team as a Product Manager and help shape the future of our health applications. In this role, you'll develop product roadmaps, gather and prioritize requirements, and coordinate cross-functional teams to deliver features that delight our users. The ideal candidate has experience managing software products, understands user-centered design principles, and can translate business needs into technical requirements. Strong analytical skills and the ability to make data-driven decisions are essential. You'll work closely with stakeholders at all levels to ensure our products meet market demands and achieve business objectives.", 
                "Strasbourg",
                JobType.FULL_TIME, 70000, 90000, ExperienceLevel.SENIOR);
        companies.get(3).addJob(job8);
        jobs.add(job8);
        
        Job job9 = new Job("Cloud Architect", 
                "We're looking for an experienced Cloud Architect to design and implement our AWS-based infrastructure. In this role, you'll develop cloud migration strategies, design scalable and secure architectures, and establish best practices for cloud resource management. The ideal candidate has deep knowledge of AWS services, experience with infrastructure as code tools, and understands security and compliance requirements in cloud environments. You should be able to optimize cloud resources for performance and cost-efficiency while ensuring high availability and disaster recovery capabilities. You'll work with technical teams to implement solutions that support our business growth and innovation goals.", 
                "Rennes",
                JobType.FREELANCE, 80000, 110000, ExperienceLevel.EXECUTIVE);
        companies.get(4).addJob(job9);
        jobs.add(job9);
        
        Job job10 = new Job("Sustainability Lead", 
                "Join our team as a Sustainability Lead and help drive our green energy initiatives. In this role, you'll develop and implement sustainability strategies, manage environmental projects, and evaluate new green technologies. The ideal candidate has knowledge of environmental regulations, experience with sustainability reporting, and a passion for renewable energy solutions. You should be able to communicate effectively with stakeholders about environmental impacts and benefits. This internship provides an opportunity to gain hands-on experience in the fast-growing green energy sector while making a positive impact on the environment.", 
                "Nantes",
                JobType.INTERNSHIP, 30000, 40000, ExperienceLevel.ENTRY_LEVEL);
        companies.get(4).addJob(job10);
        jobs.add(job10);
        
        // Save all jobs at once to create a small time difference between createdAt timestamps
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
        applications.add(new Application(jobs.get(0), applicants.get(0)));
        applications.add(new Application(jobs.get(2), applicants.get(0)));
        
        applications.add(new Application(jobs.get(1), applicants.get(1)));
        applications.add(new Application(jobs.get(3), applicants.get(1)));
        
        applications.add(new Application(jobs.get(4), applicants.get(2)));
        applications.add(new Application(jobs.get(5), applicants.get(2)));
        
        applications.add(new Application(jobs.get(6), applicants.get(3)));
        applications.add(new Application(jobs.get(7), applicants.get(3)));
        
        applications.add(new Application(jobs.get(0), applicants.get(4)));
        applications.add(new Application(jobs.get(9), applicants.get(4)));
        
        applications.add(new Application(jobs.get(2), applicants.get(5)));
        applications.add(new Application(jobs.get(8), applicants.get(5)));
        
        applications.add(new Application(jobs.get(1), applicants.get(6)));
        applications.add(new Application(jobs.get(9), applicants.get(6)));
        
        applications.add(new Application(jobs.get(3), applicants.get(7)));
        applications.add(new Application(jobs.get(4), applicants.get(7)));
        
        applications.add(new Application(jobs.get(5), applicants.get(8)));
        applications.add(new Application(jobs.get(6), applicants.get(8)));
        
        applications.add(new Application(jobs.get(7), applicants.get(9)));
        applications.add(new Application(jobs.get(8), applicants.get(9)));
        
        applicationRepository.saveAll(applications);
    }
} 