import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SoptorshiSharedModule } from 'app/shared';
import {
    CommercialPoStatusComponent,
    CommercialPoStatusDetailComponent,
    CommercialPoStatusUpdateComponent,
    CommercialPoStatusDeletePopupComponent,
    CommercialPoStatusDeleteDialogComponent,
    commercialPoStatusRoute,
    commercialPoStatusPopupRoute
} from './';

const ENTITY_STATES = [...commercialPoStatusRoute, ...commercialPoStatusPopupRoute];

@NgModule({
    imports: [SoptorshiSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        CommercialPoStatusComponent,
        CommercialPoStatusDetailComponent,
        CommercialPoStatusUpdateComponent,
        CommercialPoStatusDeleteDialogComponent,
        CommercialPoStatusDeletePopupComponent
    ],
    entryComponents: [
        CommercialPoStatusComponent,
        CommercialPoStatusUpdateComponent,
        CommercialPoStatusDeleteDialogComponent,
        CommercialPoStatusDeletePopupComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SoptorshiCommercialPoStatusModule {}
