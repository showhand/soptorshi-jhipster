import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Currency } from 'app/shared/model/currency.model';
import { CurrencyExtendedService } from './currency-extended.service';
import { CurrencyExtendedComponent } from './currency-extended.component';
import { CurrencyExtendedDetailComponent } from './currency-extended-detail.component';
import { CurrencyExtendedUpdateComponent } from './currency-extended-update.component';
import { ICurrency } from 'app/shared/model/currency.model';
import { CurrencyDeletePopupComponent } from 'app/entities/currency';

@Injectable({ providedIn: 'root' })
export class CurrencyExtendedResolve implements Resolve<ICurrency> {
    constructor(private service: CurrencyExtendedService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<ICurrency> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<Currency>) => response.ok),
                map((currency: HttpResponse<Currency>) => currency.body)
            );
        }
        return of(new Currency());
    }
}

export const currencyExtendedRoute: Routes = [
    {
        path: '',
        component: CurrencyExtendedComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Currencies'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: CurrencyExtendedDetailComponent,
        resolve: {
            currency: CurrencyExtendedResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Currencies'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: CurrencyExtendedUpdateComponent,
        resolve: {
            currency: CurrencyExtendedResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Currencies'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: CurrencyExtendedUpdateComponent,
        resolve: {
            currency: CurrencyExtendedResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Currencies'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const currencyExtendedPopupRoute: Routes = [
    {
        path: ':id/delete',
        component: CurrencyDeletePopupComponent,
        resolve: {
            currency: CurrencyExtendedResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Currencies'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
