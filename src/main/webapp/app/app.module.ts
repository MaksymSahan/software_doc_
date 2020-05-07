import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import './vendor';
import { TMobileSharedModule } from 'app/shared/shared.module';
import { TMobileCoreModule } from 'app/core/core.module';
import { TMobileAppRoutingModule } from './app-routing.module';
import { TMobileHomeModule } from './home/home.module';
import { TMobileEntityModule } from './entities/entity.module';
// jhipster-needle-angular-add-module-import JHipster will add new module here
import { MainComponent } from './layouts/main/main.component';
import { NavbarComponent } from './layouts/navbar/navbar.component';
import { FooterComponent } from './layouts/footer/footer.component';
import { PageRibbonComponent } from './layouts/profiles/page-ribbon.component';
import { ErrorComponent } from './layouts/error/error.component';

@NgModule({
  imports: [
    BrowserModule,
    TMobileSharedModule,
    TMobileCoreModule,
    TMobileHomeModule,
    // jhipster-needle-angular-add-module JHipster will add new module here
    TMobileEntityModule,
    TMobileAppRoutingModule
  ],
  declarations: [MainComponent, NavbarComponent, ErrorComponent, PageRibbonComponent, FooterComponent],
  bootstrap: [MainComponent]
})
export class TMobileAppModule {}
