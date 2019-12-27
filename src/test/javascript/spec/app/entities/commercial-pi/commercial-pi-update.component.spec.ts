/* tslint:disable max-line-length */
import { ComponentFixture, fakeAsync, TestBed, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { of } from 'rxjs';

import { SoptorshiTestModule } from '../../../test.module';
import { CommercialPiUpdateComponent } from 'app/entities/commercial-pi/commercial-pi-update.component';
import { CommercialPiService } from 'app/entities/commercial-pi/commercial-pi.service';
import { CommercialPi } from 'app/shared/model/commercial-pi.model';

describe('Component Tests', () => {
    describe('CommercialPi Management Update Component', () => {
        let comp: CommercialPiUpdateComponent;
        let fixture: ComponentFixture<CommercialPiUpdateComponent>;
        let service: CommercialPiService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SoptorshiTestModule],
                declarations: [CommercialPiUpdateComponent]
            })
                .overrideTemplate(CommercialPiUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(CommercialPiUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(CommercialPiService);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity', fakeAsync(() => {
                // GIVEN
                const entity = new CommercialPi(123);
                spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.commercialPi = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.update).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));

            it('Should call create service on save for new entity', fakeAsync(() => {
                // GIVEN
                const entity = new CommercialPi();
                spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.commercialPi = entity;
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
