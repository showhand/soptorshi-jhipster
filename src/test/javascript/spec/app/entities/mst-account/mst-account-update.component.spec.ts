/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { SoptorshiTestModule } from '../../../test.module';
import { MstAccountUpdateComponent } from 'app/entities/mst-account/mst-account-update.component';
import { MstAccountService } from 'app/entities/mst-account/mst-account.service';
import { MstAccount } from 'app/shared/model/mst-account.model';

describe('Component Tests', () => {
    describe('MstAccount Management Update Component', () => {
        let comp: MstAccountUpdateComponent;
        let fixture: ComponentFixture<MstAccountUpdateComponent>;
        let service: MstAccountService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SoptorshiTestModule],
                declarations: [MstAccountUpdateComponent]
            })
                .overrideTemplate(MstAccountUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(MstAccountUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(MstAccountService);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity', fakeAsync(() => {
                // GIVEN
                const entity = new MstAccount(123);
                spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.mstAccount = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.update).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));

            it('Should call create service on save for new entity', fakeAsync(() => {
                // GIVEN
                const entity = new MstAccount();
                spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.mstAccount = entity;
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
