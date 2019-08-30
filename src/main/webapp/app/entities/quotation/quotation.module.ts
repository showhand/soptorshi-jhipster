import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SoptorshiSharedModule } from 'app/shared';
import {
    QuotationComponent,
    QuotationDetailComponent,
    QuotationUpdateComponent,
    QuotationDeletePopupComponent,
    QuotationDeleteDialogComponent,
    quotationRoute,
    quotationPopupRoute
} from './';

const ENTITY_STATES = [...quotationRoute, ...quotationPopupRoute];

@NgModule({
    imports: [SoptorshiSharedModule, RouterModule.forChild(ENTITY_STATES)],
    /*    declarations: [
        QuotationComponent,
        QuotationDetailComponent,
        QuotationUpdateComponent,
        QuotationDeleteDialogComponent,
        QuotationDeletePopupComponent
    ],
    entryComponents: [QuotationComponent, QuotationUpdateComponent, QuotationDeleteDialogComponent, QuotationDeletePopupComponent],*/
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SoptorshiQuotationModule {}
