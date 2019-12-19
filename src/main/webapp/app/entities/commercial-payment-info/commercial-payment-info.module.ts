import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SoptorshiSharedModule } from 'app/shared';
import {
    CommercialPaymentInfoComponent,
    CommercialPaymentInfoDetailComponent,
    CommercialPaymentInfoUpdateComponent,
    CommercialPaymentInfoDeletePopupComponent,
    CommercialPaymentInfoDeleteDialogComponent,
    commercialPaymentInfoRoute,
    commercialPaymentInfoPopupRoute
} from './';

const ENTITY_STATES = [...commercialPaymentInfoRoute, ...commercialPaymentInfoPopupRoute];

@NgModule({
    imports: [SoptorshiSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        CommercialPaymentInfoComponent,
        CommercialPaymentInfoDetailComponent,
        CommercialPaymentInfoUpdateComponent,
        CommercialPaymentInfoDeleteDialogComponent,
        CommercialPaymentInfoDeletePopupComponent
    ],
    entryComponents: [
        CommercialPaymentInfoComponent,
        CommercialPaymentInfoUpdateComponent,
        CommercialPaymentInfoDeleteDialogComponent,
        CommercialPaymentInfoDeletePopupComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SoptorshiCommercialPaymentInfoModule {}
