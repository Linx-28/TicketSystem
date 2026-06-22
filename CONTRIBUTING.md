# Contributing to TicketSystem

Thank you for your interest in contributing to TicketSystem!

## How to Contribute

1. **Fork** this repository
2. **Create** your feature branch (`git checkout -b feature/AmazingFeature`)
3. **Commit** your changes (`git commit -m 'Add some AmazingFeature'`)
4. **Push** to the branch (`git push origin feature/AmazingFeature`)
5. **Open** a Pull Request

## Development Setup

1. Clone the repo
2. Import into IntelliJ IDEA
3. Configure MySQL database (see README.md)
4. Deploy to Tomcat

## Code Style

- Follow MVC architecture: Controller → Service → DAO → Entity
- Service layer: interface in `service/`, implementation in `service/impl/`
- Controller extends `BaseServlet`
- Use meaningful variable and method names
- Add Javadoc for public methods

## Pull Request Guidelines

- Keep PRs focused on a single change
- Update README.md if adding new features
- Test your changes before submitting
- Write clear commit messages

## Reporting Bugs

Open an issue with:
- Steps to reproduce
- Expected behavior
- Actual behavior
- Environment details (JDK, Tomcat, MySQL versions)
