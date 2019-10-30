import { CUSTOM_ELEMENTS_SCHEMA, NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SoptorshiSharedModule } from 'app/shared';
import {
    StockInItemDeleteDialogExtendedComponent,
    StockInItemDeletePopupComponentExtended,
    StockInItemDetailExtendedComponent,
    StockInItemExtendedComponent,
    stockInItemExtendedRoute,
    stockInItemPopupRouteExtended,
    StockInItemUpdateExtendedComponent
} from './';

const ENTITY_STATES = [...stockInItemExtendedRoute, ...stockInItemPopupRouteExtended];

@NgModule({
    imports: [SoptorshiSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        StockInItemExtendedComponent,
        StockInItemDetailExtendedComponent,
        StockInItemUpdateExtendedComponent,
        StockInItemDeleteDialogExtendedComponent,
        StockInItemDeletePopupComponentExtended
    ],
    entryComponents: [
        StockInItemExtendedComponent,
        StockInItemUpdateExtendedComponent,
        StockInItemDeleteDialogExtendedComponent,
        StockInItemDeletePopupComponentExtended
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SoptorshiStockInItemModuleExtended {}
