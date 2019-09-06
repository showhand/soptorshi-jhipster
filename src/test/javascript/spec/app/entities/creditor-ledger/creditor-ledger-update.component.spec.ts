/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { SoptorshiTestModule } from '../../../test.module';
import { CreditorLedgerUpdateComponent } from 'app/entities/creditor-ledger/creditor-ledger-update.component';
import { CreditorLedgerService } from 'app/entities/creditor-ledger/creditor-ledger.service';
import { CreditorLedger } from 'app/shared/model/creditor-ledger.model';

describe('Component Tests', () => {
    describe('CreditorLedger Management Update Component', () => {
        let comp: CreditorLedgerUpdateComponent;
        let fixture: ComponentFixture<CreditorLedgerUpdateComponent>;
        let service: CreditorLedgerService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SoptorshiTestModule],
                declarations: [CreditorLedgerUpdateComponent]
            })
                .overrideTemplate(CreditorLedgerUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(CreditorLedgerUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(CreditorLedgerService);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity', fakeAsync(() => {
                // GIVEN
                const entity = new CreditorLedger(123);
                spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.creditorLedger = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.update).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));

            it('Should call create service on save for new entity', fakeAsync(() => {
                // GIVEN
                const entity = new CreditorLedger();
                spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.creditorLedger = entity;
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
