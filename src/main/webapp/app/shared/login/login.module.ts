import { CUSTOM_ELEMENTS_SCHEMA, NgModule } from '@angular/core';
import { SoptorshiSharedModule } from 'app/shared';
import { RouterModule } from '@angular/router';
import { HOME_ROUTE, HomeComponent } from 'app/home';
import { LOGIN_ROUTE } from 'app/shared/login/login.route';
import { JhiLoginExtendedComponent } from 'app/shared/login/login-extended.component';

@NgModule({
    imports: [SoptorshiSharedModule, RouterModule.forChild([LOGIN_ROUTE])],
    declarations: [JhiLoginExtendedComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SoptorshiLoginModule {}
