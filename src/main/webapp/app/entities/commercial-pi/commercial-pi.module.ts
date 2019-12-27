import { CUSTOM_ELEMENTS_SCHEMA, NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SoptorshiSharedModule } from 'app/shared';
import {
    CommercialPiComponent,
    CommercialPiDeleteDialogComponent,
    CommercialPiDeletePopupComponent,
    CommercialPiDetailComponent,
    commercialPiPopupRoute,
    commercialPiRoute,
    CommercialPiUpdateComponent
} from './';

const ENTITY_STATES = [...commercialPiRoute, ...commercialPiPopupRoute];

@NgModule({
    imports: [SoptorshiSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        CommercialPiComponent,
        CommercialPiDetailComponent,
        CommercialPiUpdateComponent,
        CommercialPiDeleteDialogComponent,
        CommercialPiDeletePopupComponent
    ],
    entryComponents: [
        CommercialPiComponent,
        CommercialPiUpdateComponent,
        CommercialPiDeleteDialogComponent,
        CommercialPiDeletePopupComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SoptorshiCommercialPiModule {}
