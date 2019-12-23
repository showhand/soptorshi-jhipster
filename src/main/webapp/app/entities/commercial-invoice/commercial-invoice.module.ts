import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SoptorshiSharedModule } from 'app/shared';
import {
    CommercialInvoiceComponent,
    CommercialInvoiceDetailComponent,
    CommercialInvoiceUpdateComponent,
    CommercialInvoiceDeletePopupComponent,
    CommercialInvoiceDeleteDialogComponent,
    commercialInvoiceRoute,
    commercialInvoicePopupRoute
} from './';

const ENTITY_STATES = [...commercialInvoiceRoute, ...commercialInvoicePopupRoute];

@NgModule({
    imports: [SoptorshiSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        CommercialInvoiceComponent,
        CommercialInvoiceDetailComponent,
        CommercialInvoiceUpdateComponent,
        CommercialInvoiceDeleteDialogComponent,
        CommercialInvoiceDeletePopupComponent
    ],
    entryComponents: [
        CommercialInvoiceComponent,
        CommercialInvoiceUpdateComponent,
        CommercialInvoiceDeleteDialogComponent,
        CommercialInvoiceDeletePopupComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SoptorshiCommercialInvoiceModule {}
