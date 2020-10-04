import './vendor.ts';

import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { HTTP_INTERCEPTORS } from '@angular/common/http';
import { NgbDatepickerConfig } from '@ng-bootstrap/ng-bootstrap';
import { Ng2Webstorage } from 'ngx-webstorage';
import { NgJhipsterModule } from 'ng-jhipster';

import { AuthExpiredInterceptor } from './blocks/interceptor/auth-expired.interceptor';
import { ErrorHandlerInterceptor } from './blocks/interceptor/errorhandler.interceptor';
import { NotificationInterceptor } from './blocks/interceptor/notification.interceptor';
import { SoptorshiSharedModule } from 'app/shared';
import { SoptorshiCoreModule } from 'app/core';
import { SoptorshiAppRoutingModule } from './app-routing.module';
import { SoptorshiHomeModule } from './home/home.module';
import { SoptorshiAccountModule } from './account/account.module';
import { SoptorshiEntityModule } from './entities/entity.module';
import * as moment from 'moment';
// jhipster-needle-angular-add-module-import JHipster will add new module here
import {
    ErrorComponent,
    FooterComponent,
    JhiMainComponent,
    NavbarComponent,
    NavbarExtendedComponent,
    PageRibbonComponent
} from './layouts';
import { MatButtonModule, MatTableModule, MatTabsModule } from '@angular/material';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { NgxPageScrollCoreModule } from 'ngx-page-scroll-core';
import { DeviceDetectorModule } from 'ngx-device-detector';
import { AutoCompleteModule } from 'primeng/primeng';
import { Select2Module } from 'ng2-select2';
import { BreadcrumbModule } from 'angular-crumbs';
import { JhiMainExtendedComponent } from 'app/layouts/main/main-extended.component';
import { SoptorshiLoginModule } from 'app/shared/login/login.module';
import { SoptorshiExtendedHomeModule } from 'app/home-extended';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatSelectModule } from '@angular/material/select';
import { MatAutocompleteModule } from '@angular/material/autocomplete';
import { LeftSideMenuComponent } from 'app/layouts/left-side-menu/left-side-menu.component';

// import { Ng2SmartTableModule } from 'ng2-smart-table';

@NgModule({
    imports: [
        BrowserModule,
        Ng2Webstorage.forRoot({ prefix: 'jhi', separator: '-' }),
        NgJhipsterModule.forRoot({
            // set below to true to make alerts look like toast
            alertAsToast: false,
            alertTimeout: 5000
        }),
        SoptorshiSharedModule.forRoot(),
        SoptorshiCoreModule,
        SoptorshiHomeModule,
        SoptorshiExtendedHomeModule,
        SoptorshiLoginModule,
        SoptorshiAccountModule,
        // jhipster-needle-angular-add-module JHipster will add new module here
        SoptorshiEntityModule,
        SoptorshiAppRoutingModule,
        BrowserAnimationsModule,
        MatButtonModule,
        MatTabsModule,
        MatTableModule,
        MatFormFieldModule,
        MatInputModule,
        MatSelectModule,
        MatAutocompleteModule,
        DeviceDetectorModule.forRoot(),
        NgxPageScrollCoreModule,
        AutoCompleteModule,
        Select2Module,
        BreadcrumbModule
    ],
    declarations: [
        JhiMainComponent,
        JhiMainExtendedComponent,
        NavbarExtendedComponent,
        NavbarComponent,
        ErrorComponent,
        PageRibbonComponent,
        FooterComponent,
        LeftSideMenuComponent
    ],
    providers: [
        {
            provide: HTTP_INTERCEPTORS,
            useClass: AuthExpiredInterceptor,
            multi: true
        },
        {
            provide: HTTP_INTERCEPTORS,
            useClass: ErrorHandlerInterceptor,
            multi: true
        },
        {
            provide: HTTP_INTERCEPTORS,
            useClass: NotificationInterceptor,
            multi: true
        }
    ],
    exports: [ErrorComponent],
    bootstrap: [JhiMainExtendedComponent]
})
export class SoptorshiAppModule {
    constructor(private dpConfig: NgbDatepickerConfig) {
        this.dpConfig.minDate = { year: moment().year() - 100, month: 1, day: 1 };
    }
}
