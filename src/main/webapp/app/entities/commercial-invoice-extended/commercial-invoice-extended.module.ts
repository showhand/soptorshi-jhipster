import { CUSTOM_ELEMENTS_SCHEMA, NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SoptorshiSharedModule } from 'app/shared';
import {
    CommercialInvoiceDeleteDialogExtendedComponent,
    CommercialInvoiceDeletePopupExtendedComponent,
    CommercialInvoiceDetailExtendedComponent,
    CommercialInvoiceExtendedComponent,
    commercialInvoiceExtendedRoute,
    commercialInvoicePopupExtendedRoute,
    CommercialInvoiceUpdateExtendedComponent
} from './';

const ENTITY_STATES = [...commercialInvoiceExtendedRoute, ...commercialInvoicePopupExtendedRoute];

@NgModule({
    imports: [SoptorshiSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        CommercialInvoiceExtendedComponent,
        CommercialInvoiceDetailExtendedComponent,
        CommercialInvoiceUpdateExtendedComponent,
        CommercialInvoiceDeleteDialogExtendedComponent,
        CommercialInvoiceDeletePopupExtendedComponent
    ],
    entryComponents: [
        CommercialInvoiceExtendedComponent,
        CommercialInvoiceUpdateExtendedComponent,
        CommercialInvoiceDeleteDialogExtendedComponent,
        CommercialInvoiceDeletePopupExtendedComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SoptorshiCommercialInvoiceModule {}
