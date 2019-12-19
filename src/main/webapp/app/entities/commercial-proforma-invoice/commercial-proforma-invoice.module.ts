import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SoptorshiSharedModule } from 'app/shared';
import {
    CommercialProformaInvoiceComponent,
    CommercialProformaInvoiceDetailComponent,
    CommercialProformaInvoiceUpdateComponent,
    CommercialProformaInvoiceDeletePopupComponent,
    CommercialProformaInvoiceDeleteDialogComponent,
    commercialProformaInvoiceRoute,
    commercialProformaInvoicePopupRoute
} from './';

const ENTITY_STATES = [...commercialProformaInvoiceRoute, ...commercialProformaInvoicePopupRoute];

@NgModule({
    imports: [SoptorshiSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        CommercialProformaInvoiceComponent,
        CommercialProformaInvoiceDetailComponent,
        CommercialProformaInvoiceUpdateComponent,
        CommercialProformaInvoiceDeleteDialogComponent,
        CommercialProformaInvoiceDeletePopupComponent
    ],
    entryComponents: [
        CommercialProformaInvoiceComponent,
        CommercialProformaInvoiceUpdateComponent,
        CommercialProformaInvoiceDeleteDialogComponent,
        CommercialProformaInvoiceDeletePopupComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SoptorshiCommercialProformaInvoiceModule {}
