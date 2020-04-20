import { CUSTOM_ELEMENTS_SCHEMA, NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SoptorshiSharedModule } from 'app/shared';
import {
    CommercialPaymentInfoDeleteDialogExtendedComponent,
    CommercialPaymentInfoDeletePopupExtendedComponent,
    CommercialPaymentInfoDetailExtendedComponent,
    CommercialPaymentInfoExtendedComponent,
    commercialPaymentInfoExtendedRoute,
    commercialPaymentInfoPopupExtendedRoute,
    CommercialPaymentInfoUpdateExtendedComponent
} from './';

const ENTITY_STATES = [...commercialPaymentInfoExtendedRoute, ...commercialPaymentInfoPopupExtendedRoute];

@NgModule({
    imports: [SoptorshiSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        CommercialPaymentInfoExtendedComponent,
        CommercialPaymentInfoDetailExtendedComponent,
        CommercialPaymentInfoUpdateExtendedComponent,
        CommercialPaymentInfoDeleteDialogExtendedComponent,
        CommercialPaymentInfoDeletePopupExtendedComponent
    ],
    entryComponents: [
        CommercialPaymentInfoExtendedComponent,
        CommercialPaymentInfoUpdateExtendedComponent,
        CommercialPaymentInfoDeleteDialogExtendedComponent,
        CommercialPaymentInfoDeletePopupExtendedComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SoptorshiCommercialPaymentInfoExtendedModule {}
