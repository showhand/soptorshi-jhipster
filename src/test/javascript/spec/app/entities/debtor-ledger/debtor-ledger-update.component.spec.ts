/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { SoptorshiTestModule } from '../../../test.module';
import { DebtorLedgerUpdateComponent } from 'app/entities/debtor-ledger/debtor-ledger-update.component';
import { DebtorLedgerService } from 'app/entities/debtor-ledger/debtor-ledger.service';
import { DebtorLedger } from 'app/shared/model/debtor-ledger.model';

describe('Component Tests', () => {
    describe('DebtorLedger Management Update Component', () => {
        let comp: DebtorLedgerUpdateComponent;
        let fixture: ComponentFixture<DebtorLedgerUpdateComponent>;
        let service: DebtorLedgerService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SoptorshiTestModule],
                declarations: [DebtorLedgerUpdateComponent]
            })
                .overrideTemplate(DebtorLedgerUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(DebtorLedgerUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(DebtorLedgerService);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity', fakeAsync(() => {
                // GIVEN
                const entity = new DebtorLedger(123);
                spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.debtorLedger = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.update).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));

            it('Should call create service on save for new entity', fakeAsync(() => {
                // GIVEN
                const entity = new DebtorLedger();
                spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.debtorLedger = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.create).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));
        });
    });
});
