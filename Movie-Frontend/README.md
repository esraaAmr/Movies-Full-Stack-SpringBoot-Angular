# 🎬 Movie Management System – Frontend

A responsive Angular application for managing movies with user authentication, admin dashboard, and confirmation dialogs.

## 🚀 Tech Stack
- **Framework**: Angular 18.2.0 (TypeScript 5.4.0)
- **Port**: 4200
- **Backend**: [Movie-Backend](https://github.com/esraaAmr/Movie-Backend)

---

## ✨ Features
- User authentication (Login/Logout)
- Admin dashboard for movie CRUD operations
- User dashboard to view movies
- Confirmation dialogs for delete operations
- Role-based access control
- Responsive design
- Password manager prevention

---

## 🏗️ Project Structure
```
Movie-Frontend/
├── src/
│   ├── app/
│   │   ├── core/                    # Core services and guards
│   │   │   ├── guards/             # Route guards
│   │   │   └── services/           # Core services (API, Auth, HTTP)
│   │   ├── features/               # Feature modules
│   │   │   ├── auth/               # Authentication feature
│   │   │   │   └── components/login/
│   │   │   ├── admin/              # Admin feature
│   │   │   │   └── components/dashboard/
│   │   │   └── user/               # User feature
│   │   │       └── components/dashboard/
│   │   ├── shared/                 # Shared components and services
│   │   │   ├── components/         # Reusable components
│   │   │   │   ├── dialog/         # Confirmation dialogs
│   │   │   │   └── loading/        # Loading spinner
│   │   │   └── services/           # Shared services
│   │   ├── app.module.ts
│   │   └── app-routing.module.ts
│   ├── assets/
│   └── environments/
├── package.json
└── angular.json
```

---

## 🔧 Configuration
Update the API URL in `src/environments/environment.ts` if needed:

```typescript
export const environment = {
  production: false,
  apiUrl: 'http://localhost:8081'  // Backend API URL
};
```

---

## 🛠️ Installation & Setup

### 1️⃣ Clone the Frontend Repository
```bash
git clone https://github.com/esraaAmr/Movie-Frontend.git
cd Movie-Frontend
```

### 2️⃣ Install Dependencies
```bash
npm install
```

### 3️⃣ Start Development Server
Make sure the backend is running first:
```bash
ng serve
```

The app will be available at:
🔗 **http://localhost:4200**

---

## 🏃‍♂️ Quick Start

1. **Start PostgreSQL database**
2. **Start the backend**: [Movie-Backend](https://github.com/esraaAmr/Movie-Backend)
3. **Start the frontend** using `ng serve`
4. **Access the application** at http://localhost:4200

### Default Login Credentials
- **Admin**: Username and password as configured in your backend
- **User**: Username and password as configured in your backend

---

## 🧪 Testing

### Frontend Tests
```bash
ng test                    # Run unit tests
ng e2e                     # Run end-to-end tests
ng test --watch=false      # Run tests once
```

### Build for Production
```bash
ng build                   # Build for production
ng build --prod           # Build with production optimizations
```

---

## 📝 Available Scripts

```bash
ng serve              # Start development server
ng build              # Build for production
ng build --prod       # Build with production optimizations
ng test               # Run unit tests
ng e2e                # Run end-to-end tests
ng lint               # Run linting
ng generate component # Generate new component
ng generate service   # Generate new service
```

---

## 🔧 Development Notes

- **No Notification System**: The application uses console logging instead of notifications
- **Confirmation Dialogs**: All delete operations require user confirmation
- **Password Manager Prevention**: Login form includes attributes to prevent password manager interference
- **Role-based Routing**: Users are automatically redirected based on their role after login

---

## 🐛 Troubleshooting

### Common Issues
1. **API Connection Error**: Ensure the backend is running on port 8081
2. **CORS Issues**: Check backend CORS configuration
3. **Authentication Issues**: Verify backend authentication endpoints
4. **Build Errors**: Run `npm install` to ensure all dependencies are installed

### Debug Mode
- Open browser developer tools (F12)
- Check console for detailed logging
- Network tab shows API calls and responses
