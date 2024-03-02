# Contributing Guidelines

Thank you for considering contributing to our project! Your interest and support are greatly appreciated.

- [Contributing Guidelines](#contributing-guidelines)
  - [How to Contribute](#how-to-contribute)
  - [Management Info](#management-info)
  - [Tech Stack](#tech-stack)
  - [Code Rules](#code-rules)
  - [Commit Rules](#commit-rules)
  - [Let's go!](#lets-go)


## How to Contribute

1. **Management Info:** Follow project management practices outlined in the [Management Info](#management-info) section.
2. **Check Project's Tech Stack:** Familiarize yourself with the [project's tech stack](#tech-stack) to better understand its architecture and requirements.
3. **Review Documentation:** Before contributing, thoroughly review the project's [documentation](./docs/home.md) to understand its functionality, APIs, and guidelines.
5. **Select an Open Issue:** Choose an open issue from the project's issues list that aligns with your skills and interests.
6. **Create a New Branch:** From the `dev` branch, create a new branch for the selected issue following this naming template: `feature/*name*`.
7. **Commit Conventions:** Follow specific conventions for commits outlined in the [Commit Rules](#commit-rules) section below.
8. **Code Rules:** Adhere to the code rules specified in the [Code Rules](#commit-rules) section to maintain code quality.
9.  **Open Merge Requests:** Open merge requests to the `dev` branch and await review and approval/decline from project maintainers.
10. **Review Pipeline Results:** After approval to the `dev` and `test` branches, review pipeline results and address any related issues in subsequent iterations.

## Management Info
- **SDLC:** Follow [Software Development Life Cycle (SDLC)](https://en.wikipedia.org/wiki/Systems_development_life_cycle) methodologies.
- **RACI Matrix Usage:** Utilize a [Responsibility Assignment Matrix (RACI)](https://en.wikipedia.org/wiki/Responsibility_assignment_matrix) to clarify roles and responsibilities within the project team.
- **Agile Practices:** Implement Agile practices such as [Scrum](https://en.wikipedia.org/wiki/Scrum_(software_development)) and [Kanban](https://en.wikipedia.org/wiki/Kanban) for effective project management.


## Tech Stack
- Backend:
  - PostgreSQL
  - Kotlin
  - Maven
  - Apache Kafka
  - Redis
  - Kubernetes (K8s)
  - Elasticsearch
- API:
  - gRPC
  - REST
  - OpenAPI Swagger
- Production:
  - Google Kubernetes Engine
- Frontend:
  - oauth2
  <!-- - //TODO add client frontend  -->

## Code Rules
- **Architectural Rules:** Never break or deviate from the architectural rules and principles defined in documentation for the project. Adherence to architectural guidelines ensures scalability, maintainability, and robustness of the application.
- **Test-Driven Development (TDD):** Embrace [TDD](https://en.wikipedia.org/wiki/Test-driven_development) principles by writing tests before implementing functionality.
- **SonarLint Usage:** Regularly use [SonarLint](https://plugins.jetbrains.com/plugin/7973-sonarlint) to ensure adherence to code quality standards.
- **JavaDoc Guidelines:** Include all JavaDocs in interfaces files to enhance code readability and maintainability.
- **Logger Usage:** Employ logger as much as possible for effective debugging and monitoring.


## Commit Rules
- **SonarLint Integration:** Utilize SonarLint to detect and fix any warnings before committing your changes.
- **Logical Descriptions:** Provide logical descriptions or reference issue numbers rather than describing physical changes.
- **Commit Naming Convention:** Use the following naming template for commits: `:emoji: - Logical description or issue number.`
    - Emoji Codes:
        - :rocket: - `:rocket:` - New feature
        - :bug: - `:bug:` - Bug fix
        - :art: - `:art:` - Code refactoring
        - :memo: - `:memo:` - Documentation update
        - :wrench: - `:wrench:` - Configuration change
        - :fire: - `:fire:` - Removal of code
        - :hammer: - `:hammer:` - Work in progress

## Let's go!
- Go!
- Go!
- Go!