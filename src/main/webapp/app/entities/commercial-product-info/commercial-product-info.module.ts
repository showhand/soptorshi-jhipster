import { CUSTOM_ELEMENTS_SCHEMA, NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SoptorshiSharedModule } from 'app/shared';
import {
    CommercialProductInfoComponent,
    CommercialProductInfoDeleteDialogComponent,
    CommercialProductInfoDeletePopupComponent,
    CommercialProductInfoDetailComponent,
    commercialProductInfoPopupRoute,
    commercialProductInfoRoute,
    CommercialProductInfoUpdateComponent
} from './';

const ENTITY_STATES = [...commercialProductInfoRoute, ...commercialProductInfoPopupRoute];

@NgModule({
    imports: [SoptorshiSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        CommercialProductInfoComponent,
        CommercialProductInfoDetailComponent,
        CommercialProductInfoUpdateComponent,
        CommercialProductInfoDeleteDialogComponent,
        CommercialProductInfoDeletePopupComponent
    ],
    entryComponents: [
        CommercialProductInfoComponent,
        CommercialProductInfoUpdateComponent,
        CommercialProductInfoDeleteDialogComponent,
        CommercialProductInfoDeletePopupComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SoptorshiCommercialProductInfoModule {}
