import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SoptorshiSharedModule } from 'app/shared';
import {
    BillComponent,
    BillDetailComponent,
    BillUpdateComponent,
    BillDeletePopupComponent,
    BillDeleteDialogComponent,
    billRoute,
    billPopupRoute
} from './';

const ENTITY_STATES = [...billRoute, ...billPopupRoute];

@NgModule({
    imports: [SoptorshiSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [BillComponent, BillDetailComponent, BillUpdateComponent, BillDeleteDialogComponent, BillDeletePopupComponent],
    entryComponents: [BillComponent, BillUpdateComponent, BillDeleteDialogComponent, BillDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SoptorshiBillModule {}
