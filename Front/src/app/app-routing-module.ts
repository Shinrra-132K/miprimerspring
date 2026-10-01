import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { Parejacomponent } from './components/parejacomponent/parejacomponent';
import { Exparejacomponent } from './components/exparejacomponent/exparejacomponent';
import { Relacioncomponent } from './components/relacioncomponent/relacioncomponent';

const routes: Routes = [
  { path: '', redirectTo: 'parejas', pathMatch: 'full' },
  { path: 'parejas', component: Parejacomponent },
  { path: 'exparejas', component: Exparejacomponent },
  { path: 'relaciones', component: Relacioncomponent },
  { path: '**', redirectTo: 'parejas' },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}
