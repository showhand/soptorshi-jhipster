import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SoptorshiSharedModule } from 'app/shared';
import {
    QuotationDetailsComponent,
    QuotationDetailsDetailComponent,
    QuotationDetailsUpdateComponent,
    QuotationDetailsDeletePopupComponent,
    QuotationDetailsDeleteDialogComponent,
    quotationDetailsRoute,
    quotationDetailsPopupRoute
} from './';

const ENTITY_STATES = [...quotationDetailsRoute, ...quotationDetailsPopupRoute];

@NgModule({
    imports: [SoptorshiSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        QuotationDetailsComponent,
        QuotationDetailsDetailComponent,
        QuotationDetailsUpdateComponent,
        QuotationDetailsDeleteDialogComponent,
        QuotationDetailsDeletePopupComponent
    ],
    entryComponents: [
        QuotationDetailsComponent,
        QuotationDetailsUpdateComponent,
        QuotationDetailsDeleteDialogComponent,
        QuotationDetailsDeletePopupComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SoptorshiQuotationDetailsModule {}
