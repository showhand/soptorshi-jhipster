import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SoptorshiSharedModule } from 'app/shared';
import { HOME_EXTENDED_ROUTE, HomeExtendedComponent } from './';

@NgModule({
    imports: [SoptorshiSharedModule, RouterModule.forChild([HOME_EXTENDED_ROUTE])],
    declarations: [HomeExtendedComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SoptorshiExtendedHomeModule {}
