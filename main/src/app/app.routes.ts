import { Routes } from '@angular/router';
import { FullComponent } from './layouts/full/full.component';

export const routes: Routes = [
  {
    path: '',
    component: FullComponent,
    children: [
      {
        path: '',
        redirectTo: '/employee',
        pathMatch: 'full',
      },
      {
        path: 'employee',
        loadChildren: () =>
          import('./pages/pages.routes').then((m) => m.PagesRoutes),
      }
    ],
  }
];
