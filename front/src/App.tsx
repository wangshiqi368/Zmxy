// src/App.tsx
import styles from './App.module.css';
import AppRouter from './router';

function App() {
  return (
    <div className={styles.app}>
      <AppRouter />
    </div>
  );
}

export default App;
