import { CUSTOM_ELEMENTS_SCHEMA, NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SoptorshiSharedModule } from 'app/shared';
import {
    CommercialInvoiceComponent,
    CommercialInvoiceDeleteDialogComponent,
    CommercialInvoiceDeletePopupComponent,
    CommercialInvoiceDetailComponent,
    commercialInvoicePopupRoute,
    commercialInvoiceRoute,
    CommercialInvoiceUpdateComponent
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
