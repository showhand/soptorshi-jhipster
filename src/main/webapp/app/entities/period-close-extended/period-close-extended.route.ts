import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Resolve, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { IPeriodClose, PeriodClose, PeriodCloseFlag } from 'app/shared/model/period-close.model';
import { PeriodCloseExtendedService } from './period-close-extended.service';
import { PeriodCloseExtendedComponent } from './period-close-extended.component';
import { PeriodCloseExtendedDetailComponent } from './period-close-extended-detail.component';
import { PeriodCloseExtendedUpdateComponent } from './period-close-extended-update.component';
import { PeriodCloseDeletePopupComponent } from 'app/entities/period-close';
import { IFinancialAccountYear } from 'app/shared/model/financial-account-year.model';

export interface IFinancialAccountYearExtended extends IFinancialAccountYear {
    financialAccountYearId?: number;
}

@Injectable({ providedIn: 'root' })
export class PeriodCloseExtendedResolve implements Resolve<IPeriodClose> {
    constructor(private service: PeriodCloseExtendedService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IPeriodClose> {
        const id = route.params['id'] ? route.params['id'] : null;
        const financialAccountYearId = route.params['financialAccountYearId'] ? route.params['financialAccountYearId'] : null;
        console.log(route);
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<PeriodClose>) => response.ok),
                map((periodClose: HttpResponse<PeriodClose>) => periodClose.body)
            );
        } else if (financialAccountYearId) {
            const periodClose = new PeriodClose();
            periodClose.financialAccountYearId = financialAccountYearId;
            periodClose.flag = PeriodCloseFlag.OPEN;
            return of(periodClose);
        } else {
            const periodClose = new PeriodClose();
            periodClose.flag = PeriodCloseFlag.OPEN;
            return of(new PeriodClose());
        }
    }
}

export const periodCloseExtendedRoute: Routes = [
    {
        path: '',
        component: PeriodCloseExtendedComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'PeriodCloses'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: PeriodCloseExtendedDetailComponent,
        resolve: {
            periodClose: PeriodCloseExtendedResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'PeriodCloses'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: PeriodCloseExtendedUpdateComponent,
        resolve: {
            periodClose: PeriodCloseExtendedResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'PeriodCloses'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':financialAccountYearId/new',
        component: PeriodCloseExtendedUpdateComponent,
        resolve: {
            periodClose: PeriodCloseExtendedResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'PeriodCloses'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: PeriodCloseExtendedUpdateComponent,
        resolve: {
            periodClose: PeriodCloseExtendedResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'PeriodCloses'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const periodCloseExtendedPopupRoute: Routes = [
    {
        path: ':id/delete',
        component: PeriodCloseDeletePopupComponent,
        resolve: {
            periodClose: PeriodCloseExtendedResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'PeriodCloses'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
