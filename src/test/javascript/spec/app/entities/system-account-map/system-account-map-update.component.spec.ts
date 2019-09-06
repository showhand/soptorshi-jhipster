/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { SoptorshiTestModule } from '../../../test.module';
import { SystemAccountMapUpdateComponent } from 'app/entities/system-account-map/system-account-map-update.component';
import { SystemAccountMapService } from 'app/entities/system-account-map/system-account-map.service';
import { SystemAccountMap } from 'app/shared/model/system-account-map.model';

describe('Component Tests', () => {
    describe('SystemAccountMap Management Update Component', () => {
        let comp: SystemAccountMapUpdateComponent;
        let fixture: ComponentFixture<SystemAccountMapUpdateComponent>;
        let service: SystemAccountMapService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SoptorshiTestModule],
                declarations: [SystemAccountMapUpdateComponent]
            })
                .overrideTemplate(SystemAccountMapUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(SystemAccountMapUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(SystemAccountMapService);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity', fakeAsync(() => {
                // GIVEN
                const entity = new SystemAccountMap(123);
                spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.systemAccountMap = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.update).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));

            it('Should call create service on save for new entity', fakeAsync(() => {
                // GIVEN
                const entity = new SystemAccountMap();
                spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.systemAccountMap = entity;
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
